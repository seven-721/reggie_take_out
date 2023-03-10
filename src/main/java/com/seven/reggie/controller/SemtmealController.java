package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.SetmealEntity;
import com.seven.reggie.entity.dto.SetmealDto;
import com.seven.reggie.servce.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/2
 * Time:14:53
 */
@RestController
@RequestMapping("/setmeal")
@Api(tags = "套餐的Controller")
public class SemtmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     *   添加数据
     * @param setmealDto
     * @param session
     * @return
     */
    @PostMapping
    public R add(@RequestBody SetmealDto setmealDto, HttpSession session){
        Long emploId = (Long) session.getAttribute("EmployeeEntity");
        setmealDto.setCreateUser(emploId);
        setmealDto.setUpdateUser(emploId);
        R result=setmealService.add(setmealDto);
        return result;
    }

    /**
     *   分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value ="套餐的分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page" ,value = "当前页",required = false),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = false),
            @ApiImplicitParam(name = "name",value = "搜索的套餐名字",required = false)
    })
    public R page(@RequestParam(defaultValue = "1") Integer page,
                  @RequestParam(defaultValue = "10") Integer pageSize,String name){
       R result= setmealService.page(page,pageSize,name);
        return result;
    }

    /**
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除套餐")
    public R deleteById(@RequestParam List<Long> ids){
        R result=setmealService.deleteById(ids);
        return result;

    }

    @PostMapping("/status/{status}")
    public R updeteByStatus(@PathVariable("status") Integer status,@RequestParam List<Long> ids){
       R result= setmealService.updeteByStatus(status,ids);
        return result;
    }

    /**
     *  根据套餐类别查询套餐
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value ="根据套餐类别id查询套餐列表" )
    @Cacheable(value = "setmeal",key ="#categoryId+'_'+#status")
    public R list(Long categoryId,Integer status){
        R<List<SetmealEntity>> result=setmealService.list(categoryId,status);
        return result;
    }
}
