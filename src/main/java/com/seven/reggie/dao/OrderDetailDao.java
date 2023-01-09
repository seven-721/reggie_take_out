package com.seven.reggie.dao;

import com.seven.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:20:09
 */
public interface OrderDetailDao {
    void batchSave(@Param("orderDetailList") List<OrderDetail> orderDetailList);
}
