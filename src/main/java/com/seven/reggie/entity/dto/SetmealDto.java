package com.seven.reggie.entity.dto;


import com.seven.reggie.entity.SetmealDish;
import com.seven.reggie.entity.SetmealEntity;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends SetmealEntity {

    private List<SetmealDish> setmealDishes;//套餐关联的菜品集合
	
    private String categoryName;//分类名称
}