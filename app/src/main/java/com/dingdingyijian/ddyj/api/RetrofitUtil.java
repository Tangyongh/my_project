package com.dingdingyijian.ddyj.api;

import android.annotation.SuppressLint;

import com.dingdingyijian.ddyj.AppConfig;
import com.dingdingyijian.ddyj.net.BaseUrlInterceptor;
import com.dingdingyijian.ddyj.net.HeadInterceptor;
import com.dingdingyijian.ddyj.net.LogInterceptor;
import com.dingdingyijian.ddyj.net.callback.CustomGsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.Proxy;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 网络请求 工具
 */
public class RetrofitUtil {
    private static final int TIME_OUT = 60 * 1000;//链接超时时间
    private static RetrofitUtil sRetrofitUtil;
    private ApiService mApiService;

    public static RetrofitUtil getInstance() {
        if (sRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (sRetrofitUtil == null) {
                    sRetrofitUtil = new RetrofitUtil();
                }
            }
        }
        return sRetrofitUtil;
    }


    public RetrofitUtil() {
        Gson gson = new GsonBuilder()
                .setLenient() // json宽松
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //时间转化为特定格式
                .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                .setPrettyPrinting() //对结果进行格式化，增加换行
                .disableHtmlEscaping() //防止特殊字符出现乱码
                .create();
        //添加log拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                //添加log拦截器
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new HeadInterceptor())
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.getHostUrl())
                .addConverterFactory(CustomGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    // HTTPS 相关
    private static class TrustAllCerts implements X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }

    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}
