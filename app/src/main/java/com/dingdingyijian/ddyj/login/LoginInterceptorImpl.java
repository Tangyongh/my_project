package com.dingdingyijian.ddyj.login;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.LoginUtils;

/**
 * @author: DDYiJian
 * @time: 2022/3/24
 * @describe: 登录拦截器
 */
@Interceptor(name = "login", priority = 1)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        boolean isLogin = LoginUtils.isLogin();
        if (isLogin) { // 如果已经登录不拦截
            callback.onContinue(postcard);
        } else { // 如果没有登录
            switch (path) {
                //需要先登录
                case Constant.PATH_WEB_CONTENT:
                    callback.onInterrupt(null);
                    ARouter.getInstance().build(Constant.PATH_LOGIN_PWD).navigation();
                    break;
                // 不需要登录
                default:
                    callback.onContinue(postcard);
                    break;
            }
        }
    }

    @Override
    public void init(Context context) {
        Logger.d("LoginInterceptorImpl", "拦截器初始化");
    }
}
