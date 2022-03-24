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
package com.dingdingyijian.ddyj.wxapi;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.dingdingyijian.ddyj.pay.IPayCallback;
import com.dingdingyijian.ddyj.pay.IPayStrategy;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 文 件 名: WXPay
 * 创 建 人: King
 * 创建日期: 2017/2/13 19:03
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 *
 * @see <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN">Des</a>
 */
public class WXPay implements IPayStrategy<WXPayInfo> {

    private static WXPay mWXPay;
    private WXPayInfo payInfoImpli;
    private static IPayCallback sPayCallback;
    private IWXAPI mWXApi;
    private boolean initialization;

    private WXPay() {

    }

    public static WXPay getInstance() {
        if (mWXPay == null) {
            synchronized (WXPay.class) {
                if (mWXPay == null) {
                    mWXPay = new WXPay();
                }
            }
        }
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    private void initWXApi(Context context, String appId) {
        mWXApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), appId);
        mWXApi.registerApp(appId);
        initialization = true;
    }

    @Override
    public void pay(AppCompatActivity activity, WXPayInfo payInfo, IPayCallback payCallback) {
        this.payInfoImpli = payInfo;
        sPayCallback = payCallback;

        if (payInfoImpli == null || TextUtils.isEmpty(payInfoImpli.getAppid()) || TextUtils.isEmpty(payInfoImpli.getPartnerid())
                || TextUtils.isEmpty(payInfoImpli.getPrepayId()) || TextUtils.isEmpty(payInfoImpli.getPackageValue()) ||
                TextUtils.isEmpty(payInfoImpli.getNonceStr()) || TextUtils.isEmpty(payInfoImpli.getTimestamp()) ||
                TextUtils.isEmpty(payInfoImpli.getSign())) {
            if (payCallback != null) {
                payCallback.onError(WXErrCodeEx.CODE_ILLEGAL_ARGURE, WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_ILLEGAL_ARGURE));
            }
            return;
        }

        if (!initialization) {
            initWXApi(activity.getApplicationContext(), payInfoImpli.getAppid());
        }

        if (!check()) {
            if (payCallback != null) {
                payCallback.onError(WXErrCodeEx.CODE_UNSUPPORT, WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_UNSUPPORT));
            }
            return;
        }
        PayReq req = new PayReq();
        req.appId = payInfoImpli.getAppid();
        req.partnerId = payInfoImpli.getPartnerid();
        req.prepayId = payInfoImpli.getPrepayId();
        req.packageValue = payInfoImpli.getPackageValue();
        req.nonceStr = payInfoImpli.getNonceStr();
        req.timeStamp = payInfoImpli.getTimestamp();
        req.sign = payInfoImpli.getSign();
        mWXApi.sendReq(req);
    }

    /**
     * 支付回调响应
     */
    public void onResp(int errorCode, String errorMsg) {
        if (sPayCallback == null) {
            return;
        }
        if (errorCode == BaseResp.ErrCode.ERR_OK) {
            sPayCallback.onSuccess();
        } else if (errorCode == BaseResp.ErrCode.ERR_COMM) {
            sPayCallback.onError(errorCode, errorMsg);
        } else if (errorCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            sPayCallback.onCancel();
        } else {
            sPayCallback.onError(errorCode, errorMsg);
        }
        sPayCallback = null;
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}