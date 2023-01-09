package com.seven.reggie.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.seven.reggie.dao.EmployeeDao;
import com.seven.reggie.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageTest {

    @Autowired(required = false)
    private EmployeeDao employeeDao;

    /**
     * 测试PageHepler使用分页
     */
    @Test
    public void testPage(){
        //1. 通知PageHelper当前页与页面大小
        PageHelper.startPage(1,1);//参数一：当前页  参数2： 页面大小
        //2. 查询页面的数据
        List<EmployeeEntity> employeeList = employeeDao.findByName(null);

        //3.创建一个Pageinfo对象，然后把页面的数据传递进去即可。
        PageInfo<EmployeeEntity> pageInfo = new PageInfo<>(employeeList) ; // PageInfo 就是封装整个分页对象的数据
        System.out.println("当前页："+ pageInfo.getPageNum());
        System.out.println("页面大小："+ pageInfo.getPageSize());
        System.out.println("当前页数据："+ pageInfo.getList());
        System.out.println("总记录数："+ pageInfo.getTotal());
        System.out.println("总页数："+ pageInfo.getPages());
    }

}
