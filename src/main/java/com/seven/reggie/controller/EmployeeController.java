package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.EmployeeEntity;
import com.seven.reggie.entity.Page;
import com.seven.reggie.servce.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/26
 * Time:14:57
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {


    @Autowired(required = false)
    private EmployeeService employeeService;

        //用户登录
        @PostMapping("/login")
        public R login(@RequestBody EmployeeEntity employeeEntity, HttpSession session, HttpServletResponse response){
            //根据传入得用户名查找用户
             R<EmployeeEntity> result=employeeService.loginByName(employeeEntity);

            if (result.getCode()==1) {
                EmployeeEntity employeeEntity1=result.getData();
              /*  Cookie cookie=new Cookie("name",employeeEntity1.getName());
                response.addCookie(cookie);*/
                session.setAttribute("EmployeeEntity",employeeEntity1.getId());
            }
            return result;
        }

    //退出登录
    @PostMapping("/logout")
    public R logout(HttpSession session){

        session.invalidate();
        return R.success("成功退出!!");
    }

    //添加员工
    @PostMapping
    public R add(@RequestBody EmployeeEntity employeeEntity, HttpSession session){

        Long emplo = (Long) session.getAttribute("EmployeeEntity");
        System.out.println(emplo);
        employeeEntity.setCreateUser(emplo);//创建人
        employeeEntity.setUpdateUser(emplo);//更新人
        R result=employeeService.add(employeeEntity);
        return result;
    }

    //分页
    @GetMapping("/page")
    public R page(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize, String name){
       //如果传参不是json格式我们是可以直接定义同名变量去接收即可
        R<Page> result=employeeService.page(page,pageSize,name);

        return result;
    }
    //修改以及禁用
    @PutMapping
    public R update(@RequestBody EmployeeEntity employeeEntity,HttpSession session){//由于传参是json传，所以需要添加@RequestBody注解
        //补全更新人信息
        long empId= (long) session.getAttribute("EmployeeEntity");
        employeeEntity.setUpdateUser(empId);
        R result=employeeService.update(employeeEntity);
        return result;
    }

    /**
     * 根据id查找用户数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Long id){
        R<EmployeeEntity> result=employeeService.findById(id);
        return result;
    }

}
