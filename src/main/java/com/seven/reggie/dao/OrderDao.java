package com.seven.reggie.dao;

import com.seven.reggie.entity.Orders;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:19:53
 */
public interface OrderDao {


    @Insert("insert into orders values(#{id},#{number},#{status},#{userId},#{addressBookId}," +
            "#{orderTime},#{checkoutTime},#{payMethod},#{amount},#{remark},#{phone},#{address},#{userName},#{consignee})")
    void save(Orders orders);

}
