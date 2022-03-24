package com.dingdingyijian.ddyj.pay;


import androidx.annotation.Nullable;

/**
 * 支付回调
 */
public interface IPayCallback {
    /**
     * 支付成功
     */
    void onSuccess();

    /**
     * 支付失败
     */
    void onError(int code, @Nullable String message);


    /**
     * 取消支付
     */
    void onCancel();
}
