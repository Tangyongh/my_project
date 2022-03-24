package com.dingdingyijian.ddyj.mvp.data;

import android.text.TextUtils;

import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

public class LoginInfo {


    /**
     * 登录接口参数
     *
     * @param userName  手机号
     * @param pwd       密码
     * @param loginType 登录类型
     * @return
     */
    public static LoginBean getLoginBean(String userName, String pwd, String loginType) {
        //判断地图定位获取的位置信息是否为空
        if (TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LONGITUDE)) ||
                TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LATITUDE)) ||
                TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADDRESS)) || TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE))) {
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LONGITUDE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LATITUDE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADCODE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LAST_SHORT_ADDRESS, "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADDRESS, "");
        }
        LoginBean loginBean = new LoginBean();
        loginBean.setLon(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LONGITUDE));
        loginBean.setLat(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LATITUDE));
        loginBean.setLastAddress(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADDRESS));
        loginBean.setLastAcArea(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE));
        loginBean.setLastShortAddress(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LAST_SHORT_ADDRESS));
        loginBean.setRegIdAlias(PreferenceUtil.getInstance().getString(ConstantOther.APP_PUSH_ID));
        loginBean.setUserName(userName);
        loginBean.setLoginType(loginType);
        loginBean.setTerminal("android");
        loginBean.setImei("");
        //loginType : 2是验证码登录  1是密码登录  4是微信登录
        loginBean.setValiCode(loginType.equals("2") ? pwd : "");
        //密码登录的时候需要密码，其他传空
        loginBean.setPassword(loginType.equals("1") ? pwd : "");
        return loginBean;
    }

    /**
     * 缓存用户登录信息字段
     *
     * @param loginBean
     */
    public static void setUserInfo(LoginBean loginBean) {
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_PHONE, loginBean.getMobile()); //手机号
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_TOKEN, loginBean.getLoginToken()); //token
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_ID, loginBean.getId());//用户id
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_LAT, loginBean.getLat());
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_LON, loginBean.getLon());
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_NICK_NAME, loginBean.getNickName());
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_RC_TOKEN, loginBean.getRcToken());
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_WX_UNIONID, loginBean.getWxUnionid());
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_LOGIN_NAME, loginBean.getUserName());
    }

    /**
     * 清除用户登录缓存信息
     */
    public static void cleanUserInfo() {
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_TOKEN);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_USER_ID);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_USER_LAT);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_USER_LON);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_USER_NICK_NAME);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_PHONE);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_RC_TOKEN);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_WX_UNIONID);
        PreferenceUtil.getInstance().removeKey(ConstantOther.KEY_APP_USER_LOGIN_NAME);
    }

}
