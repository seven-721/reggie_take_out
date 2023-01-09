package com.seven.reggie.dao;

import com.seven.reggie.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/26
 * Time:14:58
 */
public interface EmployeeDao {
    @Select("select * from employee where username=#{username}")
    EmployeeEntity loginByName(EmployeeEntity employeeEntity);

    //添加用户
    @Insert("INSERT into employee values (null,#{username},#{name},#{password},#{phone},#{sex}," +
            "#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    int add(EmployeeEntity employeeEntity);


    //根据名字查找员工列表
    List<EmployeeEntity>findByName(@Param("name") String name);

    int update(EmployeeEntity employeeEntity);

    //根据Id查找用户信息
    @Select("select * from employee where id=#{id}")
    EmployeeEntity findById(Long id);
}
