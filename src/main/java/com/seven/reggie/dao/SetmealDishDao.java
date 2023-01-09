package com.seven.reggie.dao;

import com.seven.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:15:11
 */
public interface SetmealDishDao {


    void bachInsert(@Param("setmealDishList") List<SetmealDish> setmealDishList);

    void deleteById(@Param("ids") List<Long> ids);
}
