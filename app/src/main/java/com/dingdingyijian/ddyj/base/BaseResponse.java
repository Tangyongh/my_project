package com.dingdingyijian.ddyj.base;

import com.dingdingyijian.ddyj.utils.Constant;

/**
 * BaseResponse<T>
 */
public class BaseResponse<T> {



    private String message;
    private int code;
    private String timestamp;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public boolean isSuccess() {
        //后台协议的网络请求成功code值
        return code == Constant.successCode;
    }

}
