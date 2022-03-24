package com.dingdingyijian.ddyj.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.dingdingyijian.ddyj.R;

import java.util.Objects;

public class LoadingProgressDialog {
    public Dialog dialog;

    public void createLoadingDialog(Context context) {
        // 首先得到整个View
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null);
        // 创建自定义样式的Dialog
        dialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setDimAmount(0f);
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.show();
        }

    }

    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
