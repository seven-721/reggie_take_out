package com.seven.reggie.dao;

import com.seven.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:18:52
 */
public interface DishFlavorDao {
    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> findByDishId(Long id);

    //根据dish_id删除数据
    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteByDishId(Long id);

    //插入数据

    void batchInsert(@Param("flavorList") List<DishFlavor> flavorList);


}
