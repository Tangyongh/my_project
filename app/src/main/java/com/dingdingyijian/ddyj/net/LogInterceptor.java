package com.dingdingyijian.ddyj.net;

import androidx.annotation.NonNull;

import com.dingdingyijian.ddyj.utils.Logger;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * Log拦截器
 */
public class LogInterceptor implements Interceptor {


    @SuppressWarnings("NullableProblems")
    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        Logger.e("LogInterceptor", response.request().url() + "");
        assert response.body() != null;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.json(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
