package com.dingdingyijian.ddyj.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.dingdingyijian.ddyj.App;
import com.dingdingyijian.ddyj.view.CustomToast;
import com.dingdingyijian.ddyj.view.MyToast;

import java.util.Date;

/**
 * Created by xp on 2016/3/28.
 */
public class ToastUtil {


    public static void showMsg(Context context, String msg, int duration) {
        CustomToast.showToast(context, msg);
    }

    public static void showMsg(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        CustomToast.showToast(context, msg);
    }
}
