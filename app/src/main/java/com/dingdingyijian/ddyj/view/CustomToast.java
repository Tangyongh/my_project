package com.dingdingyijian.ddyj.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dingdingyijian.ddyj.R;


/**
 * 自定义toast
 *
 * @author xp
 * @describe 自定义toast.
 * @date 2017/9/19.
 */

public class CustomToast {
    private static TextView mMessage;

    public static void showToast(Context context, String message) {
        try {
            //加载Toast布局
            @SuppressLint("InflateParams") View toastRoot = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
            //初始化布局控件
            mMessage = toastRoot.findViewById(R.id.tv_message);
            //为控件设置属性
            mMessage.setText(message);
            //Toast的初始化
            Toast toastStart = new Toast(context);
            //获取屏幕高度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            toastStart.setGravity(Gravity.TOP, 0, height / 2 - 20);
            toastStart.setDuration(Toast.LENGTH_SHORT);
            toastStart.setView(toastRoot);
            toastStart.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
