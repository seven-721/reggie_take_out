package com.seven.reggie.dao;

import com.seven.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:15:07
 */
@Mapper
public interface AddressBookDao {

    //查询地址列表

    @Select("select * from address_book where user_id=#{userId} order by update_time desc")
    List<AddressBook> queryAddressList(Long userId);

    
    //新增
    //添加地址
    @Insert("insert into address_book(user_id,consignee,phone,sex,detail,label,create_time,update_time," +
            "create_user,update_user) " +
            "values(#{userId},#{consignee},#{phone},#{sex},#{detail},#{label},#{createTime},#{updateTime},#{createUser}," +
            "#{updateUser})")
    void add(AddressBook addressBook);

    @Select("select * from address_book where id=#{id}")
    AddressBook findById(Long userId);

    @Select(("select * from address_book where  user_id = #{userId} and is_default = 1"))
    AddressBook getDefaultAddress(Long userId);

    @Update("update  address_book set is_default=0 where user_id=#{userId}")
    void removeDefaultAddress(Long userId);

    @Update(("update address_book set is_default=1 where id=#{id}"))
    void updeteDefaultAddress(AddressBook addressBook);
    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Long userId);
}
