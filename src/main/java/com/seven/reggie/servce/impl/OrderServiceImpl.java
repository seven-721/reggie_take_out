package com.seven.reggie.servce.impl;

import com.seven.reggie.dao.*;
import com.seven.reggie.entity.*;
import com.seven.reggie.servce.OrderService;
import com.seven.reggie.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:19:53
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private OrderDao _orderDao;

    @Autowired(required = false)
    private ShoppingCartDao _shoppingCartDao;

    @Autowired(required = false)
    private UserDao _userDao;

    @Autowired(required = false)
    private OrderDetailDao _orderDetailDao;

    @Autowired
    private AddressBookDao _addressBookDao;

    @Override
    public void save(Orders orders) {
        //A. 获得当前用户id, 查询当前用户的购物车数据
        Long userId = orders.getUserId();
        List<ShoppingCart> shoppingCartList=_shoppingCartDao.findByUsersId(userId);

        //B. 根据当前登录用户id, 查询用户数据
        User user=_userDao.findById(userId);

        //C. 根据地址ID, 查询地址数据
      AddressBook addressBook= _addressBookDao.getById(orders.getAddressBookId());

        //D. 组装订单明细数据, 批量保存订单明细
        long longValue = UUIDUtils.getUUIDInOrderId().longValue();

        BigDecimal bigDecimal = new BigDecimal("0.0");
        List<OrderDetail> orderDetailList=new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(UUIDUtils.getUUIDInOrderId().longValue());
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setOrderId(longValue);
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setAmount(shoppingCart.getAmount());

            bigDecimal=bigDecimal.add(shoppingCart.getAmount().multiply
                    (new BigDecimal(shoppingCart.getNumber()+"")));
                orderDetail.setImage(shoppingCart.getImage());
                orderDetailList.add(orderDetail);
        }
        //E. 组装订单数据, 批量保存订单数据
        orders.setId(longValue);
        orders.setNumber(longValue+"");
        orders.setStatus(1);
        orders.setOrderTime(LocalDateTime.now());
        orders.setAmount(bigDecimal);
        orders.setUserName(user.getName());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());

        _orderDao.save(orders);

        _orderDetailDao.batchSave(orderDetailList);
        //F. 删除当前用户的购物车列表数据
        _shoppingCartDao.deleteByUserId(userId);
    }
}
