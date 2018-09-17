package com.example.demosb.Exception;

/**
 * 自定义的异常
 */
public class MyException extends RuntimeException {

    private int code;
    private String msg;
    public MyException(ErrorEnum errorEnum){
        this.code= errorEnum.getCode();
        this.msg= errorEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
