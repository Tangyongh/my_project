package com.dingdingyijian.ddyj.net.callback;


/**
 * @author: tyh
 * @date: 2022/3/19 3:43
 * @description: 统一处理服务器返回的请求错误信息
 */
public class ApiException extends RuntimeException {

    private static String errorMessage;
    private static int code;


    public ApiException(int code,String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}
