/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                              By(King)
**                         
**------------------------------------------------------------------------------
*/
package com.dingdingyijian.ddyj.pay;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 描述 ：策略模式场景类。
 * 调用 : 实例化支付策略payway,以及支付订单信息，作为参数直接传入。
 *      使用方法1：调用EasyPay.pay()方法即可。
 *      使用方法2：实例化payStrategy,直接调用其pay方法。如：new Alipay().pay(...)
 */
public class PayUtils {
    public static <T extends IPayInfo> void post(IPayStrategy<T> payWay, AppCompatActivity activity, T payinfo, IPayCallback callback){
        payWay.pay(activity, payinfo, callback);
    }
}
