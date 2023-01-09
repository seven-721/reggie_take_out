package com.seven.reggie.servce;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.SetmealEntity;
import com.seven.reggie.entity.dto.SetmealDto;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:14:58
 */
public interface SetmealService {
    //添加数据
    R add(SetmealDto setmealDto);

    //分页查询
    R page(Integer page, Integer pageSize, String name);

    //删除数据
    R deleteById(List<Long> ids);
    //修改状态
    R updeteByStatus(Integer status, List<Long> ids);

    R<List<SetmealEntity>> list(Long categoryId, Integer status);



}
