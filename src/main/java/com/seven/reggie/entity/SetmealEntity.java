package com.seven.reggie.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐
 */
@Data
@ApiModel("套餐")
public class SetmealEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    //分类id
    @ApiModelProperty("id")
    private Long categoryId;

    //套餐名称
    @ApiModelProperty("套餐名称")
    private String name;

    //套餐价格
    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    //状态 0:停用 1:启用
    @ApiModelProperty("状态")
    private Integer status;

    //编码
    @ApiModelProperty("套餐编码")
    private String code;

    //描述信息
    @ApiModelProperty("描述信息")
    private String description;

    //图片
    @ApiModelProperty("图片")
    private String image;

    //创建时间
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    //修改时间
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    //创建人
    @ApiModelProperty("创建人")
    private Long createUser;

    //修改人
    @ApiModelProperty("修改人")
    private Long updateUser;
}