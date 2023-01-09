package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.User;
import com.seven.reggie.servce.UserService;
import com.seven.reggie.util.SMSUtils;
import com.seven.reggie.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:19:42
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session){
        //生成四位数的验证码
        //Integer integer = ValidateCodeUtils.generateValidateCode(4);

        Integer integer = 5031;

        //获取手机号
        String phone = user.getPhone();
        //"调用工具类发送信息
        //SMSUtils.sendMessage("黑马旅游网","SMS_205126318",phone,integer+"");

        SMSUtils.sendMessage("七锦","SMS_266990458",phone,integer+"");

        log.info("本次验证码："+ integer);
        //将验证码存入到session中
        //session.setAttribute(phone,integer+"");

        redisTemplate.opsForValue().set(phone,integer,1, TimeUnit.MINUTES);

        return R.success("发送成功!");
    }

    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> paramMap,HttpSession session){
        //1. 获取手机号与验证码
        String phone = (String) paramMap.get("phone");
        String code = (String) paramMap.get("code");
        //2. 从session获取系统生成的验证码
        //String sessioncode = (String) session.getAttribute(phone);

        ValueOperations opsForValue = redisTemplate.opsForValue();
        String verifycode = opsForValue.get(phone).toString();

        //3.传给service去登录
        R result =_userService.login(phone,code,verifycode);
        //判断登录是否成功，如果成功则清楚session中的验证码，并且需要在session中做登录成功标识
        if (result.getCode()==1) {
            //清楚session中的验证了记录
            session.removeAttribute(phone);
            User user = (User) result.getData();
            //登录成功后将用户的id存入session中做登录成功标识
            session.setAttribute("user",user.getId());
        }
        return result;
    }


}
