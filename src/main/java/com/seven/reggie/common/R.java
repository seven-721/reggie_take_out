package com.seven.reggie.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("返回结果")
public class R<T> implements Serializable {
    @ApiModelProperty("编码")
    private Integer code; //编码：1成功，0和其它数字为失败
    @ApiModelProperty("错误信息")
    private String msg; //错误信息
    @ApiModelProperty("数据")
    private T data; //数据，数据不知道是什么类型，这里使用了泛型类。

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }
    public static  R error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
  
}