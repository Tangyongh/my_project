package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginCodeContract;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.helper.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/18 16:40
 * @description:
 */
@SuppressWarnings("ALL")
public class LoginCodeModel implements LoginCodeContract.Model {

    /**
     * 验证码登录
     *
     * @param transformer
     * @param loginBean
     * @param context
     * @param callBack
     */
    @Override
    public void getLogin(LoginBean loginBean, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .login(loginBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<LoginBean>(context) {


                    @Override
                    public void onSuccess(LoginBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }

                });
    }

    /**
     * 设置极光推送别名
     *
     * @param transformer
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getAlias(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .setAlias(ConstantUtils.convertMapToBody(hashMap))
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<String>(context, false) {

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
     * 发送登录验证码
     *
     * @param transformer
     * @param hashMap
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
