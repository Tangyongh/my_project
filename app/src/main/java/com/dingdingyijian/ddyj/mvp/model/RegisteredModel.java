package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.api.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.mvp.contract.RegisteredContract;
import com.dingdingyijian.ddyj.net.callback.ObserverCall;
import com.dingdingyijian.ddyj.net.callback.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/19 18:19
 * @description: 注册
 */
@SuppressWarnings("ALL")
public class RegisteredModel implements RegisteredContract.Model {


    /**
     * 注册
     *
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getRegistered(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .registered(ConstantUtils.convertMapToBody(hashMap))
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new ObserverCall<String>(context) {
                    @Override
                    public void onSuccess(String result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }
                });
    }

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @param type
     * @param context
     * @param callBack
     */
    @Override
    public void getSendCode(String mobile, String type, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userSendCode(mobile, type)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new ObserverCall<String>(context) {


                    @Override
                    public void onSuccess(String result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }

                });

    }
}
