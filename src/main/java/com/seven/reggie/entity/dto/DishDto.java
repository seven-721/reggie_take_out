package com.seven.reggie.entity.dto;

import com.seven.reggie.entity.DishEntity;
import com.seven.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/31
 * Time:18:03
 */
@Data
public class DishDto extends DishEntity {
    private List<DishFlavor> flavors=new ArrayList<>();
    private String categoryName;    //菜品名称
    private  Integer copies;
}
