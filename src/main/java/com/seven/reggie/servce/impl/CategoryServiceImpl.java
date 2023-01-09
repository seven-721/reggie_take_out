package com.seven.reggie.servce.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.reggie.common.R;
import com.seven.reggie.dao.CategoryDao;
import com.seven.reggie.dao.DishDao;
import com.seven.reggie.dao.SetmealDao;
import com.seven.reggie.entity.CategoryEntity;
import com.seven.reggie.entity.Page;
import com.seven.reggie.exception.CustomerException;
import com.seven.reggie.servce.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:15:02
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryDao categoryDao;
    //引入菜品Dao接口
    @Autowired(required = false)
    private DishDao dishDao;
    //引入套餐Dao接口
    @Autowired(required = false)
    private SetmealDao setmealDao;

    /**
     * 添加分类
     *
     * @param categoryEntity
     * @return
     */
    @Override
    public R add(CategoryEntity categoryEntity) {
        categoryEntity.setCreateTime(LocalDateTime.now());//创建时间
        categoryEntity.setUpdateTime(LocalDateTime.now());//修改时间
        int rows = categoryDao.add(categoryEntity);
        if (rows > 0) {
            return R.success("添加成功!");
        } else {
            return R.error("添加失败!");
        }
    }

    /**
     * 分页
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public R<Page> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CategoryEntity> categoryEntityList = categoryDao.page();
        PageInfo<CategoryEntity> categoryEntityPageInfo = new PageInfo<>(categoryEntityList);
        Page<CategoryEntity> entityPage = new Page<>(categoryEntityPageInfo.getList(), categoryEntityPageInfo.getTotal());

        return R.success(entityPage);
    }

    /**
     * 修改分类
     *
     * @param categoryEntity
     */
    @Override
    public void update(CategoryEntity categoryEntity) {
        categoryEntity.setUpdateTime(LocalDateTime.now());
        categoryDao.update(categoryEntity);
    }

    /**
     * 根据Id删除数据
     *
     * @param id
     * @return
     */
    @Override
    public R deleteById(Long id) {
        // 根据类别的id查询这个类别是否有关联菜品
        int categoryId = dishDao.findCountByCategoryId(id);
        if (categoryId > 0) {
            throw new CustomerException("该类别与其它菜品又关联，不能直接删除!");
        }
        int setmeaId = setmealDao.findCountByCategoryId(id);
        if (setmeaId > 0) {
            throw new CustomerException("该类别与其它菜品又关联，不能直接删除!");
        }

        int rows = categoryDao.deleteById(id);
        if (rows > 0) {
            return R.success("删除成功!");
        } else {
            return R.error("删除失败!");
        }
    }

    /**
     *  查询所有信息
     * @return
     */
    @Override
    public List<CategoryEntity> findAll() {

        return   categoryDao.findAll();
    }

    @Override
    public R list(Integer type) {
        List<CategoryEntity> categoryEntityList=categoryDao.list(type);
        return R.success(categoryEntityList);
    }
}