package com.dingdingyijian.ddyj.mvp.data;

import android.text.TextUtils;

import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/19 23:11
 * @description: 注册提交参数
 */
public class RegisteredInfo {

    public static HashMap<String,String> getParams(String mobile,String password,String smsCode){
        if (TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LONGITUDE)) ||
                TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LATITUDE)) ||
                TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADDRESS)) || TextUtils.isEmpty(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE))) {
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LONGITUDE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LATITUDE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADCODE, "0");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LAST_SHORT_ADDRESS, "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADDRESS, "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY_REGADDRESS, "");
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("mobile",mobile);
        hashMap.put("password",password);
        hashMap.put("parentUid","");
        hashMap.put("smsCode",smsCode);
        hashMap.put("terminal","android");
        hashMap.put("terminalType","ddyj");
        hashMap.put("imei","");
        hashMap.put("regShortAddress",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_CITY_REGADDRESS));
        hashMap.put("lon",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LONGITUDE));
        hashMap.put("lat",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LATITUDE));
        hashMap.put("regAddress",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADDRESS));
        hashMap.put("regAcArea",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE));
        hashMap.put("regCityArea",PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE));
        return hashMap;
    }
}
