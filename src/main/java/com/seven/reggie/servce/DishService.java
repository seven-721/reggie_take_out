package com.seven.reggie.servce;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.dto.DishDto;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:18:07
 */
public interface DishService {
    //菜品分页
    R page(Integer page, Integer pageSize, String name);

    //根据Id查找数据
    R findById(Long id);

    R updateById(DishDto dishDto);

    //添加数据
    R add(DishDto dishDto);

    //删除数据
    R deleteById(Long id);


    R<List<DishDto>> findCategoryId(Long categoryId, Integer status);
}
