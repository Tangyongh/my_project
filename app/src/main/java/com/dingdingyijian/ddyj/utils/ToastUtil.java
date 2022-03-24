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

    static long oldTime = 0;

    public static void showMsg(String msg, int duration) {
        if (new Date().getTime() - oldTime > 2000) {
            oldTime = new Date().getTime();
            CustomToast.showToast(App.instance.getContext(), msg);
        }
    }

    public static void showMsg(String msg) {
        try {
            if (new Date().getTime() - oldTime > 2000) {
                oldTime = new Date().getTime();
                if (msg == null || TextUtils.isEmpty(msg)) {
                    return;
                }
                CustomToast.showToast(App.instance.getContext(), msg);
            }
        }catch (Exception e){
          e.printStackTrace();  
        }
    }

    public static void showMsgLong(String msg) {
        CustomToast.showToast(App.instance.getContext(), msg);
    }

    private static MyToast toast;

    public static void showToast(Context context, int code, String content) {
        //code=1时Toast显示的时间长，code=0时显示的时间短。
        try {
            if (toast == null) {
                if (code == 0)
                    toast = MyToast.makeText(context, content, Toast.LENGTH_SHORT);
                if (code == 1)
                    toast = MyToast.makeText(context, content, Toast.LENGTH_LONG);
            } else {
                toast.setText(content);
            }
            toast.show();
        } catch (Exception e) {
        }
    }
}
