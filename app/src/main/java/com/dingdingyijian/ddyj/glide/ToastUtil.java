package com.dingdingyijian.ddyj.glide;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author SherlockHolmes
 */
public class ToastUtil {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public ToastUtil() {

    }

    public static ToastUtil getInstance() {
        return InnerClass.instance;
    }

    public void shorts(final Context context, final String text) {
        HANDLER.post(() -> Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show());
    }

    public void longs(final Context context, final String text) {
        HANDLER.post(() -> Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show());
    }

    private static class InnerClass {
        private static ToastUtil instance = new ToastUtil();
    }
}