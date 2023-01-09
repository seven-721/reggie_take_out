package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.Orders;
import com.seven.reggie.servce.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:19:53
 */
@RestController
@RequestMapping("/order")
public class OrderController {

        @Autowired(required = false)
        private OrderService _orderService;

    /**
     *  用户下单
     * @param orders
     * @param session
     * @return
     */
        @PostMapping("submit")
        public R submit(@RequestBody Orders orders , HttpSession session){
            Long userId= (Long) session.getAttribute("user");
            //把订单补全用户 Id
            orders.setUserId(userId);
            _orderService.save(orders);
            return R.success("下单成功!");



        }
}
