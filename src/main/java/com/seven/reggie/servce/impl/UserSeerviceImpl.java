package com.seven.reggie.servce.impl;

import com.seven.reggie.common.R;
import com.seven.reggie.dao.UserDao;
import com.seven.reggie.entity.User;
import com.seven.reggie.servce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:19:45
 */
@Service
public class UserSeerviceImpl implements UserService {

    @Autowired(required = false)
    private UserDao _userDao;

    @Override
    public R login(String phone, String code, String sessioncode) {

        if (code.equals(sessioncode)) {
            User user = _userDao.findPhone(phone);
            if (user != null) {
             return R.success(user);
            } else {
                User user1 = new User();
                user1.setPhone(phone);
                user1.setStatus(1);
                _userDao.insert(user1);
                return R.success(user1);
            }
        } else {
            return R.error("验证码错误!");
        }
    }
}
