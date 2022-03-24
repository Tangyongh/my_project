package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.api.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.contract.ForgetPwdContract;
import com.dingdingyijian.ddyj.api.BaseObserver;
import com.dingdingyijian.ddyj.api.callback.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/20 16:32
 * @description:
 */
@SuppressWarnings("all")
public class ForgetPwdModel implements ForgetPwdContract.Model {
    /**
     * 设置新密码
     *
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getSetPassword(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .forgetPwd(ConstantUtils.convertMapToBody(hashMap))
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<String>(context) {
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
     * 获取验证码
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
                .subscribe(new BaseObserver<String>(context) {


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
