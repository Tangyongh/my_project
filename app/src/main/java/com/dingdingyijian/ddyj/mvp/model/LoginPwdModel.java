package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.api.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginPwdContract;
import com.dingdingyijian.ddyj.net.callback.ObserverCall;
import com.dingdingyijian.ddyj.net.callback.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class LoginPwdModel implements LoginPwdContract.Model {


    /**
     * 密码登录
     * @param loginBean
     * @param context
     * @param callBack
     */
    @Override
    public void getLogin(LoginBean loginBean, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .login(loginBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new ObserverCall<LoginBean>(context) {

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
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getAlias( HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .setAlias(ConstantUtils.convertMapToBody(hashMap))
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new ObserverCall<String>(context,false) {

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
