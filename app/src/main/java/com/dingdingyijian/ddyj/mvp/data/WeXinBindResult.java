package com.dingdingyijian.ddyj.mvp.data;

import android.text.TextUtils;

import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/25
 * @describe: com.dingdingyijian.ddyj.mvp.data
 */
public class WeXinBindResult {

    public static HashMap<String,String> getParams(String mobile, String smsCode,String wxOpenid,String wxUnionid,String nickName){
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
        hashMap.put("valiCode",smsCode);
        hashMap.put("wxOpenid",wxOpenid);
        hashMap.put("wxUnionid",wxUnionid);
        hashMap.put("nickName",nickName);
        hashMap.put("terminal","android");
        hashMap.put("lastShortAddress", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_CITY_REGADDRESS));
        hashMap.put("lon", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LONGITUDE));
        hashMap.put("lat", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_LATITUDE));
        hashMap.put("lastAddress", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADDRESS));
        hashMap.put("lastAcArea", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE));
        return hashMap;
    }
}
