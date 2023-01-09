package com.seven.reggie.dao;

import com.seven.reggie.entity.CategoryEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:15:01
 */
public interface CategoryDao {
    //添加数据
    @Insert("insert into category values (null,#{type},#{name},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser} )")
    int add(CategoryEntity categoryEntity);

    @Select("select * from category order by sort asc ")
    List<CategoryEntity> page();

    //修改分类
    @Update("update category set name=#{name}, sort=#{sort}," +
            "update_Time=#{updateTime},update_User=#{updateUser} where id=#{id}")
    void update(CategoryEntity categoryEntity);

    //根据id删除数据
    @Delete("delete from category where id=#{id}")
    int deleteById(Long id);

    //查询所有信息
    @Select("select * from category")
    List<CategoryEntity> findAll();


    List<CategoryEntity> list(@Param("type") Integer type);

    //根据id查找
    @Select("select  * from category where id=#{id} ")
    CategoryEntity findById(Long categoryId);
}
