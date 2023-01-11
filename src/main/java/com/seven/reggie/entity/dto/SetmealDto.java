package com.seven.reggie.entity.dto;


import com.seven.reggie.entity.SetmealDish;
import com.seven.reggie.entity.SetmealEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel("套餐的Dto")
public class SetmealDto extends SetmealEntity {


    private List<SetmealDish> setmealDishes;//套餐关联的菜品集合

    @ApiModelProperty("类别名字")
    private String categoryName;//分类名称
}