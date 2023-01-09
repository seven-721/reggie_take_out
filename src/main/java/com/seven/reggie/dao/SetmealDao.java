package com.seven.reggie.dao;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.SetmealEntity;
import com.seven.reggie.entity.dto.SetmealDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:16:46
 */

//套餐类接口
public interface SetmealDao {
    @Select("select count(*) from setmal where  category_id=#{id}")
    int findCountByCategoryId(Long id);

    //添加数据
/*    @Insert("insert into setmeal values (null,#{categoryId},#{name},#{price},#{status},#{code}," +
            "#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")*/
    @Insert("insert into setmeal values(null,#{categoryId},#{name},#{price},#{status},#{code},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
  @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void add(SetmealDto setmealDto);

    //根据名字查询数据
    List<SetmealEntity> page(@Param("name") String name);

    Long findCountById(@Param("ids") List<Long> ids);

    void deleteById(@Param("ids") List<Long> ids);


    int updeteByStatus(@Param("status") Integer status, @Param("ids") List<Long> ids);

    @Select("select * from setmeal where category_Id=#{categoryId} and status=#{status}")
    List<SetmealEntity> list(Long categoryId, Integer status);
}
