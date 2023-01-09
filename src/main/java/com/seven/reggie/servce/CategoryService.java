package com.seven.reggie.servce;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.CategoryEntity;
import com.seven.reggie.entity.Page;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:15:01
 */
public interface CategoryService {
    //添加数据
    R add(CategoryEntity categoryEntity);
    //分页
    R<Page> page(Integer page, Integer pageSize);


    void update(CategoryEntity categoryEntity);

    R deleteById(Long id);

    List<CategoryEntity> findAll();

    R list(Integer type);
}
