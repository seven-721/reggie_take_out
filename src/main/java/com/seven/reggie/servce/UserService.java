package com.seven.reggie.servce;

import com.seven.reggie.common.R;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:19:45
 */
public interface UserService {
    //验证码登录
    R login(String phone, String code, String sessioncode);
}
