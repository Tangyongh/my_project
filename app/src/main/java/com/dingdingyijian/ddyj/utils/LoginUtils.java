package com.dingdingyijian.ddyj.utils;

import android.text.TextUtils;

/**
 * Created by tyh on 2019/3/19
 */
public class LoginUtils {

    /**
     * 标识是否登录
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isLogin() {
        if(TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_TOKEN))) {
            return false;
        }
        return true;
    }
}
