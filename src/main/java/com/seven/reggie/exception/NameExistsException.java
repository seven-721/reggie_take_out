package com.seven.reggie.exception;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/27
 * Time:15:38
 */

/**
 * 该类是我们自定义的异常类，如果出现了该异常，表示用户名已经存在
 */
public class NameExistsException extends RuntimeException{

    public NameExistsException(String message) {
        super(message);
    }
}
