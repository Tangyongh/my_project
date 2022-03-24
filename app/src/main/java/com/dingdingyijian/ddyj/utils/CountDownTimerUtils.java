package com.dingdingyijian.ddyj.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.dingdingyijian.ddyj.R;

/**
 * @author: tyh
 * @date: 2022/3/19 0:09
 * @description: 倒计时
 */
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        long seconds = Math.round((double) millisUntilFinished / 1000); //四舍五入，解决倒数计时不准确问题
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(seconds + "s");  //设置倒计时时间
        mTextView.setBackgroundResource(R.drawable.shape_bg_code); //设置按钮为灰色，这时是不能点击的
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setBackgroundResource(R.drawable.shape_bg_code);  //还原背景色
    }
}
