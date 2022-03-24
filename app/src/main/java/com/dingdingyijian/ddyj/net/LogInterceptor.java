package com.dingdingyijian.ddyj.net;

import androidx.annotation.Nullable;

import com.dingdingyijian.ddyj.utils.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;

/**
 *  TODO Log拦截器代码
 */
public class LogInterceptor implements Interceptor{
    private String TAG = "okhttp";

    @Override
    public okhttp3.Response intercept(@Nullable Chain chain) throws IOException {
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
      //  Logger.e(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
          //      response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.json(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
