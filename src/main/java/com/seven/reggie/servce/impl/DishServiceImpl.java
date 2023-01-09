package com.seven.reggie.servce.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.reggie.common.R;
import com.seven.reggie.dao.CategoryDao;
import com.seven.reggie.dao.DishDao;
import com.seven.reggie.dao.DishFlavorDao;
import com.seven.reggie.entity.CategoryEntity;
import com.seven.reggie.entity.DishEntity;
import com.seven.reggie.entity.DishFlavor;
import com.seven.reggie.entity.Page;
import com.seven.reggie.entity.dto.DishDto;
import com.seven.reggie.entity.dto.SetmealDto;
import com.seven.reggie.servce.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:18:07
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired(required = false)
    private CategoryDao categoryDao;

    @Autowired(required = false)
    private DishFlavorDao dishFlavorDao;

    @Autowired(required = false)
    private DishDao dishDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 分页
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R page(Integer page, Integer pageSize, String name) {
        //1. 设置当前页与页面大小
        PageHelper.startPage(page, pageSize);
        //2. 查询菜品的数据，根据名字去搜索
        List<DishEntity> dishList = dishDao.page(name);
        //3. 创建PageInfo对象，PageInfo对象里面的数据目前是Dish对象？
        PageInfo<DishEntity> pageInfo = new PageInfo<>(dishList);
        //4. 从pageInfo对象获取所有的Dish，然后把Dish使用stream流转化为DishDTO
        //每一个dish我都需要转换为DIshDTo
        List<DishDto> dishDtoList = pageInfo.getList().stream().map(dish -> {
            //创建一个DishDTO
            DishDto dishDto = new DishDto();
            //需要把dish的属性值拷贝到dishDto对象里面
            BeanUtils.copyProperties(dish, dishDto);  //属性拷贝的前提：两个对象的属性名与类型一致就可以拷贝，如果不一致的字段则不拷贝。
            //类别的名字
            CategoryEntity category = categoryDao.findById(dish.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        //5. 创建一个Page对象封装对应的数据返回即可。
        Page<DishDto> pageResult = new Page<>(dishDtoList, pageInfo.getTotal());
        return R.success(pageResult);
    }

    /**
     * 根据Id查找数据
     *
     * @param id
     * @return
     */
    @Override
    public R findById(Long id) {
        DishEntity dishEntity = dishDao.findById(id);

        List<DishFlavor> dishFlavorList = dishFlavorDao.findByDishId(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dishEntity, dishDto);
        dishDto.setFlavors(dishFlavorList);
        return R.success(dishDto);
    }

    /**
     * 根据id修改数据
     *
     * @param dishDto
     * @return
     */
    @Override
    public R updateById(DishDto dishDto) {
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDao.update(dishDto);
        dishFlavorDao.deleteByDishId(dishDto.getId());

        List<DishFlavor> flavorList = dishDto.getFlavors();
        if (flavorList != null && flavorList.size() > 0) {
            flavorList = flavorList.stream().map(dishFlavor -> {
                dishFlavor.setDishId(dishDto.getId());
                dishFlavor.setUpdateTime(dishDto.getUpdateTime());
                dishFlavor.setUpdateUser(dishDto.getUpdateUser());
                dishFlavor.setCreateTime(dishDto.getCreateTime());
                dishFlavor.setCreateUser(dishDto.getCreateUser());
                return dishFlavor;
            }).collect(Collectors.toList());
            dishFlavorDao.batchInsert(flavorList);
            //修改完成后清除redis的所有菜品信息
            Set keys = redisTemplate.keys("dish_*");
            redisTemplate.delete(keys);

        }


        return R.success("修改成功!");
    }

    /**
     * 添加数据
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional //在springboot不需要额外开启事务的，只需要在方法上添加@Transactional就已经开启事务了
    public R add(DishDto dishDto) {
        //1. 补全dish的参数
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setSort(0);
        //2. 插入菜品
        dishDao.add(dishDto); //只有插入了菜品之后才有菜品的id

        //3. 从dishDto取出口味列表，然后给每一个口味都补全参数
        List<DishFlavor> flavorList = dishDto.getFlavors();
        if (flavorList != null && flavorList.size() > 0) {
            flavorList = flavorList.stream().map(dishFlavor -> {
                dishFlavor.setDishId(dishDto.getId()); //所属的菜品的id
                dishFlavor.setCreateUser(dishDto.getCreateUser());
                dishFlavor.setUpdateUser(dishDto.getUpdateUser());
                dishFlavor.setCreateTime(dishDto.getCreateTime());
                dishFlavor.setUpdateTime(dishDto.getUpdateTime());
                return dishFlavor;
            }).collect(Collectors.toList());

            //4. 批量插入口味信息
            dishFlavorDao.batchInsert(flavorList);
            //更新完毕后我们将清楚redis的所有菜品缓存
            Set keys = redisTemplate.keys("dish_*");
            redisTemplate.delete(keys);
        }
        return R.success("添加成功");
    }

    @Override
    public R deleteById(Long id) {

        return null;
    }

    @Override
    public R<List<DishDto>> findCategoryId(Long categoryId, Integer status) {
        //List<DishEntity> dishList = dishDao.list(categoryId,status);
        List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get("dish_" + categoryId + "_" + status);
        if (dishDtoList == null) {
            List<DishEntity> dishList = dishDao.list(categoryId,status);
            //2.遍历所有的dish，把dish转换为dishDTO，并且查询菜品口味
            dishDtoList = dishList.stream().map(dish -> {
                DishDto dishDto = new DishDto();
                //属性拷贝
                BeanUtils.copyProperties(dish, dishDto);
                //查询菜品的口味信息，并且设置到dto里面
                List<DishFlavor> dishFlavorList = dishFlavorDao.findByDishId(dish.getId());
                dishDto.setFlavors(dishFlavorList);
                return dishDto;
            }).collect(Collectors.toList());
            redisTemplate.opsForValue().set("dish_"+categoryId+"_"+status,dishDtoList);
        }

        return R.success(dishDtoList);
    }


}
