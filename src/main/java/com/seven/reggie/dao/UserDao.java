package com.seven.reggie.dao;

import com.seven.reggie.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:19:44
 */
public interface UserDao {
    //根据手机号查询
    @Select("select * from user where phone=#{phone}")
    User findPhone(String phone);

    //插入数据
    @Insert("insert into user(phone,status) values(#{phone},#{status})  ")
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "id")
    void insert(User user1);

    @Select("select * from user where id=#{userId}")
    User findById(Long userId);
}
