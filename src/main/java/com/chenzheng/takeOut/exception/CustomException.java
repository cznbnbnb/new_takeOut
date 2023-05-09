package com.chenzheng.takeOut.exception;
//抛出自定义异常
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
