package com.seven.reggie.servce.impl;

import com.seven.reggie.common.R;
import com.seven.reggie.dao.ShoppingCartDao;
import com.seven.reggie.entity.ShoppingCart;
import com.seven.reggie.servce.ShoppingCartService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:17:54
 */

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired(required = false)
    private ShoppingCartDao _shoppingCartDao;

    @Override
    public R<ShoppingCart> add(ShoppingCart shoppingCart) {
        //根据用户ID与菜品id 或者套餐的id查询用户是否购买了该商品
       ShoppingCart shoppingCart1= _shoppingCartDao.findByUserId(shoppingCart);
       //判断如果没有购买，然后设置数量，然后插入该购买记录
        if (shoppingCart1==null) {
            //数据库没有该用户的购买该商品的记录，则需要插入新的记录
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            _shoppingCartDao.insert(shoppingCart);
            //把新加的购物项赋值给 shoppingCart1 ，因为后面只返回 shoppingCart1 代表最新购物项数，
            shoppingCart1=shoppingCart;
        }else {
            //如果已经购买的，那么 Number 数量需要加 1
            shoppingCart1.setNumber(shoppingCart1.getNumber()+1);
            _shoppingCartDao.update(shoppingCart1);
        }
        //返回当前购物项数据
        return R.success(shoppingCart1);
    }

    /**
     *  根据id查询购物车
     * @param userId
     * @return
     */
    @Override
    public R<List<ShoppingCart>> list(Long userId) {
        List<ShoppingCart> shoppingCartList=_shoppingCartDao.findByUsersId(userId);
        return R.success(shoppingCartList);
    }

    @Override
    public void deleteClean(Long userId) {
        _shoppingCartDao.deleteClean(userId);
    }
}
