package com.seven.reggie.dao;

import com.seven.reggie.entity.DishEntity;
import com.seven.reggie.entity.dto.DishDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:16:47
 */
public interface DishDao {
    @Select("select count(*) from dish where category_id =#{id}")
    int findCountByCategoryId(Long id);

    List<DishEntity> page(@Param("name") String name);

    //根据Id查找数据
    @Select("select  * from dish where id=#{id}")
    DishEntity findById(Long id);

    //修改数据
    void update(DishDto dishDto);

    //添加数据
    @Insert("insert into dish values (null,#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")

    @Options(useGeneratedKeys = true ,keyColumn = "id",keyProperty = "id")
    void add(DishDto dishDto);

    //根据categoryId查找数据

    List<DishEntity> list(Long categoryId, Integer status);
}
