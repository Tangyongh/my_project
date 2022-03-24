package com.dingdingyijian.ddyj.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dingdingyijian.ddyj.R;


/**
 * Created by Riggion on 2018/1/24 0024 17:25
 */

public class MyToast {
    private Toast mToast;
    private TextView mTextView;

    private MyToast(Context context, CharSequence text, int duration) {
        try {
            View v = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
            mTextView = v.findViewById(R.id.textView1);
            mTextView.setText(text);
            mToast = new Toast(context);
            mToast.setDuration(duration);
            mToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 100);
            mToast.setView(v);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void setText(String content) {
        mTextView.setText(content);
    }
}
