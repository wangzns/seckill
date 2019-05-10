package com.imooc.seckill.vo;

import lombok.Data;

/**
 * @author : wang zns
 * @date : 2019-05-08
 */
@Data
public class Result<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    public static<T>  Result<T>  success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    public  static <T>  Result<T> success(T data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static<T>  Result<T>  error() {

        Result result = new Result();
        result.setSuccess(false);
        result.setCode(-1);
        result.setMsg("失败");
        return result;
    }

    public static<T>  Result<T>  error(String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(-1);
        result.setMsg(message);
        return result;
    }


}
