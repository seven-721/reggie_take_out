package com.seven.reggie.servce.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.reggie.common.R;
import com.seven.reggie.dao.CategoryDao;
import com.seven.reggie.dao.SetmealDao;
import com.seven.reggie.dao.SetmealDishDao;
import com.seven.reggie.entity.*;
import com.seven.reggie.entity.dto.SetmealDto;
import com.seven.reggie.exception.CustomerException;
import com.seven.reggie.servce.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:14:59
 */

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired(required = false)
    private SetmealDao setmealDao;

    @Autowired(required = false)
    private SetmealDishDao setmealDishDao;

    @Autowired(required = false)
    private CategoryDao categoryDao;

    /**
     *   添加数据
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public R add(SetmealDto setmealDto) {
         setmealDto.setCreateTime(LocalDateTime.now());
         setmealDto.setUpdateTime(LocalDateTime.now());
         setmealDao.add(setmealDto);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList = setmealDishList.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDish.setSort(0);
            setmealDish.setCreateUser(setmealDto.getCreateUser());
            setmealDish.setUpdateUser(setmealDto.getUpdateUser());
            setmealDish.setCreateTime(setmealDto.getCreateTime());
            setmealDish.setUpdateTime(setmealDto.getUpdateTime());
            return setmealDish;
        }).collect(Collectors.toList());
        for (SetmealDish setmealDish : setmealDishList) {
            System.out.println(setmealDish.getSetmealId());
        }
        //插入菜品
        setmealDishDao.bachInsert(setmealDishList);
        return R.success("添加成功!");
    }

    /**
     *   分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R page(Integer page, Integer pageSize, String name) {
        //1. 设置当前页与页面大小
        PageHelper.startPage(page,pageSize);
        //2. 查询菜品的数据，根据名字去搜索
        List<SetmealEntity> setmealList = setmealDao.page(name);
        //3. 创建PageInfo对象，PageInfo对象里面的数据目前是Dish对象？
        PageInfo<SetmealEntity> pageInfo = new PageInfo<>(setmealList);
        //4. 从pageInfo对象获取所有的Dish，然后把Dish使用stream流转化为DishDTO
        //每一个dish我都需要转换为DIshDTo
        List<SetmealDto> setmealDtoList = pageInfo.getList().stream().map(dish -> {
            //创建一个DishDTO
            SetmealDto setmealDto = new SetmealDto();
            //需要把dish的属性值拷贝到dishDto对象里面
            BeanUtils.copyProperties(dish, setmealDto);  //属性拷贝的前提：两个对象的属性名与类型一致就可以拷贝，如果不一致的字段则不拷贝。
            //类别的名字
            CategoryEntity category = categoryDao.findById(dish.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        //5. 创建一个Page对象封装对应的数据返回即可。
        Page<SetmealDto> pageResult = new Page<>(setmealDtoList,pageInfo.getTotal());
        return R.success(pageResult);
    }

    /**
     *   删除数据
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public R deleteById(List<Long> ids) {
        //根据id查询套餐是否在售
       Long count= setmealDao.findCountById(ids);

        if (count>0) {
            throw new CustomerException("该商品存在在售状态不能删除");
        }

        setmealDao.deleteById(ids);
        //删除套餐的菜品
        setmealDishDao.deleteById(ids);
        return R.success("删除成功!") ;
    }

    @Override
    public R updeteByStatus(Integer status, List<Long> ids) {
        int rows=setmealDao.updeteByStatus(status,ids);
        if (rows>0) {
            return R.success("修改成功!");
        }else {
            return R.error("修改失败!");
        }

    }

    @Override
    public R<List<SetmealEntity>> list(Long categoryId, Integer status) {
        List<SetmealEntity>setmealEntityList=setmealDao.list(categoryId,status);
        return R.success(setmealEntityList);
    }


}
