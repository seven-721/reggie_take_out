package com.seven.reggie.servce;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.ShoppingCart;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:17:54
 */
public interface ShoppingCartService {
    //添加购物车
    R<ShoppingCart> add(ShoppingCart shoppingCart);


    R<List<ShoppingCart>> list(Long userId);

    //删除购物车
    void deleteClean(Long userId);
}
