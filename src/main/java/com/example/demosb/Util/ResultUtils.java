package com.example.demosb.Util;

import com.example.demosb.Entity.Result;
import com.example.demosb.Exception.ErrorEnum;

public class ResultUtils {
    public static Result sussess(ErrorEnum errorEnum, Object object){
        Result result = new Result();
        result.setCode(errorEnum.getCode());
        result.setMsg(errorEnum.getMsg());
        result.setData(object);
        return result;
    }

    public static Result error(ErrorEnum errorEnum){
        Result result = new Result();
        result.setCode(errorEnum.getCode());
        result.setMsg(errorEnum.getMsg());
        result.setData(null);
        return result;
    }

    public static Result error(int code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setMsg("登录失败");
        result.setCode(401);
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(null);
        return result;
    }

}
