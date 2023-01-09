package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.AddressBook;
import com.seven.reggie.entity.ShoppingCart;
import com.seven.reggie.servce.AddressBookService;
import com.seven.reggie.servce.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:15:17
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService _shoppingCartService;


   /* //查询指定用户的全部地址
    @GetMapping("/list")
    public R list(){
        //这里为了让前端代码不报错，这里一定放入一个空的集合
        List list = new ArrayList();
        return R.success(list);
    }*/

    /**
     * 添加购物车
     * @param shoppingCart
     * @param session
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        Long userId= (Long) session.getAttribute("user");
        shoppingCart.setUserId(userId);
        R<ShoppingCart> result=_shoppingCartService.add(shoppingCart);
        return result;
    }

    @GetMapping("/list")
    public R list(HttpSession session){
        //得到当前登录用户
        Long userId= (Long) session.getAttribute("user");
        //根据id查询数据
        R<List<ShoppingCart>> result=_shoppingCartService.list(userId);
        return result;
    }


    @DeleteMapping("clean")
    public R deleteClean(HttpSession session){
        Long userId= (Long) session.getAttribute("user");

        _shoppingCartService.deleteClean(userId);
        return R.success("删除成功!");

    }
}
