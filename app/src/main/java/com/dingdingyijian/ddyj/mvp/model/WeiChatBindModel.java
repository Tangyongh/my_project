package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.WeiChatBindContract;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.net.helper.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/25
 * @describe: com.dingdingyijian.ddyj.mvp.model
 */
@SuppressWarnings("all")
public class WeiChatBindModel implements WeiChatBindContract.Model {

    //微信绑定手机号
    @Override
    public void getWeiXinBind(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userWeiXinBind(ConstantUtils.convertMapToBody(hashMap))
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

    //登录
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

    //设置极光推送别名
    @Override
    public void getAlias(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .setAlias(ConstantUtils.convertMapToBody(hashMap))
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

    //获取验证码
    @Override
    public void getSendCode(String mobile, String type, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userSendCode(mobile,type)
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
