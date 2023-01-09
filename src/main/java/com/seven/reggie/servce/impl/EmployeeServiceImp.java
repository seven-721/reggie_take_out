package com.seven.reggie.servce.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.reggie.common.R;
import com.seven.reggie.dao.EmployeeDao;
import com.seven.reggie.entity.EmployeeEntity;
import com.seven.reggie.entity.Page;
import com.seven.reggie.exception.NameExistsException;
import com.seven.reggie.servce.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/26
 * Time:14:58
 */
@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired(required = false)
    private EmployeeDao employeeDao;

    //用户登录
    @Override
    public R<EmployeeEntity> loginByName(EmployeeEntity employeeEntity) {
        System.out.println(employeeEntity);
        //根据用户名查找用户
        EmployeeEntity dbemployee = employeeDao.loginByName(employeeEntity);
        System.out.println(dbemployee);

        //判断用户名是否存在
        if (dbemployee == null) {
            return R.error("用户不存在!");
        }
        //判断密码是否相等
        String password = employeeEntity.getPassword();
        String dbpassword = dbemployee.getPassword();
        //讲用户输入得明文密码加密后对比
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        //判断密码是否相等
        if (!dbpassword.equals(md5DigestAsHex)) {
            return R.error("密码错误!");
        }
        //判断用户状态是否为启用
        if (dbemployee.getStatus() == 0) {
            return R.error("对不起，您登录得用户已被禁用!");
        }
        //如果以上都通过则代表登录成功并返回登录结果
            return R.success(dbemployee);
    }

    //添加用户
    @Override
    public R add(EmployeeEntity employeeEntity) {
        //添加员工之前，先检查username是否可以找到员工对象。如果能够查找到代表用户名已经存在了，抛出名字已经存在的异常
        EmployeeEntity employee = employeeDao.loginByName(employeeEntity);
        if (employee != null) {
            throw new NameExistsException(" 用户名" + employeeEntity.getUsername() + "已经存在");
        }
        //补全密码，状态、创建时间、修改时间
        employeeEntity.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeEntity.setStatus(1);
        employeeEntity.setCreateTime(LocalDateTime.now());
        employeeEntity.setUpdateTime(LocalDateTime.now());
        int row = employeeDao.add(employeeEntity);
        if (row > 0) {
            //插入成功
            return R.success("添加成功!");
        } else {
            return R.error("添加失败!");
        }
    }


    /* @Override
     public R<Page> page(Integer page, Integer pageSize, String name) {
         PageHelper.startPage(page,pageSize);
         List<EmployeeEntity> entityList=employeeDao.findByName(name);
         for (EmployeeEntity employee : entityList) {
             System.out.println(employee);
         }
         PageInfo<EmployeeEntity> pageInfo=new PageInfo<>(entityList);
         Page<EmployeeEntity> entityPage=new Page<>(pageInfo.getList(),pageInfo.getTotal());
         return R.success(entityPage);
     }*/
    //分页
    @Override
    public R<Page> page(Integer page, Integer pageSize, String name) {
        //1. 设置当前页与页面的大小
        PageHelper.startPage(page, pageSize);
        //2 .查询页面的数据
        List<EmployeeEntity> employeeList = employeeDao.findByName(name);
        //3. 创建PageInfo对象，然后把集合传入
        PageInfo<EmployeeEntity> pageInfo = new PageInfo<>(employeeList);
        //4. 把PageInfo对象的数据转移到Page对象
        Page<EmployeeEntity> pageResult = new Page<>(pageInfo.getList(), pageInfo.getTotal());
        //5. 返回的R对象

        return R.success(pageResult);
    }

    /**
     * 修改用户状态
     * @param employeeEntity
     * @return
     */
    @Override
    public R update(EmployeeEntity employeeEntity) {
        employeeEntity.setUpdateTime(LocalDateTime.now());
        int rows = employeeDao.update(employeeEntity);
        if (rows > 0) {
            return R.success("更新成功!");
        } else {
            return R.error("更新失败!");
        }
    }

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    @Override
    public R<EmployeeEntity> findById(Long id) {
        EmployeeEntity employee=employeeDao.findById(id);
        return R.success(employee);
    }
}
