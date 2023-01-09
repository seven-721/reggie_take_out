package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.dto.DishDto;
import com.seven.reggie.servce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:18:06
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired(required = false)
    private DishService dishService;

    @GetMapping("/page")
    public R page(@RequestParam(defaultValue = "1") Integer page,
                  @RequestParam(defaultValue = "5") Integer pageSize,String name){
        R result=dishService.page(page,pageSize,name);
        System.out.println(result);
        return result;
    }

    /**
     * 根据Id查找数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable("id") Long id){
       R result= dishService.findById(id);
        return result;
    }

    //修改数据
    @PutMapping
    public  R update (@RequestBody DishDto dishDto, HttpSession session){
        //从session中获取登录者Id
        Long emploId= (Long) session.getAttribute("EmployeeEntity");
        //补齐数据
        dishDto.setUpdateUser(emploId);
        R result=dishService.updateById(dishDto);
        return result;
    }

    //添加数据
    @PostMapping
    public R add(@RequestBody DishDto dishDto,HttpSession session){
        Long emploId= (Long) session.getAttribute("EmployeeEntity");
        dishDto.setUpdateUser(emploId);
        dishDto.setCreateUser(emploId);
        R result=dishService.add(dishDto);
        return result;
    }

    //删除
    @DeleteMapping
    public R deleteById(Long id){
        R result=dishService.deleteById(id);
        return result;
    }


    //根据categoryId查找数据
    @GetMapping("/list")
    public R list(Long categoryId , Integer status){
        R<List<DishDto>> result=dishService.findCategoryId(categoryId,status);

        return result;
    }
}
