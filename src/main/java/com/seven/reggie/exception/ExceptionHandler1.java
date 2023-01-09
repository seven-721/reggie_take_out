package com.seven.reggie.exception;

import com.seven.reggie.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/27
 * Time:15:45
 */
@RestControllerAdvice
public class ExceptionHandler1 {


   // @ExceptionHandler1(NameExistsException.class )

    @ExceptionHandler(value = NameExistsException.class)
    public R HandlerNameException(NameExistsException e){
        return R.error(e.getMessage());
    }

    @ExceptionHandler(value = CustomerException.class)
    public R HandlerCustomerException(CustomerException e){
        return R.error(e.getMessage());
    }
    @ExceptionHandler(value = Exception.class)
    public R HandlerException(Exception e){
        e.printStackTrace();
        return R.error("出现错误请稍后再试!");
    }
}
