package com.dingdingyijian.ddyj.net;


import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 统一添加请求头
 */
public class HeadInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@Nullable Chain chain) throws IOException {
        String token = PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_TOKEN);
        assert chain != null;
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("app_token",(!TextUtils.isEmpty(token) ? token : ""))
                .build();
        return chain.proceed(request);
    }
}
