package com.seven.reggie.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/30
 * Time:16:51
 */

public class CustomerException extends RuntimeException{
    public CustomerException(String message) {
        super(message);
    }
}
