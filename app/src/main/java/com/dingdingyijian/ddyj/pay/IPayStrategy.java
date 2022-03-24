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
 * 描述 ：统一支付接口。策略模式中统一算法接口。
 */
public interface IPayStrategy<T extends IPayInfo> {
    void pay(AppCompatActivity activity, T payInfo, IPayCallback payCallback);
}
