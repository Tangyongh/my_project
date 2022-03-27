package com.dingdingyijian.ddyj;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.ups.JPushUPSManager;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * @author: DDYiJian
 * @time: 2022/3/10
 * @describe: com.dingdingyijian.ddyj
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static App instance;
    public Context mContext;


    @SuppressWarnings("rawtypes")
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);
            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            method.invoke(field.get(null));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
        instance = this;
        initMMKV();
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .setExcludeFontScale(true);// 屏蔽系统字体大小
        initJPush(this);
        initUM();
        initLiveEvent();
        setRxJavaErrorHandler();
    }


    private void initUM() {
        // preInit预初始化函数耗时极少，不会影响App首次冷启动用户体验
        UMConfigure.preInit(this, "5c92113661f564ad4600113f", "umeng");
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */

        UMConfigure.init(this, "5c92113661f564ad4600113f", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        //开启Log
        UMConfigure.setLogEnabled(AppConfig.isDebug());
        PlatformConfig.setWeixin(ConstantOther.WX_APPID, ConstantOther.WX_SECRET);
        PlatformConfig.setWXFileProvider("com.dingdingyijian.ddyj.fileprovider");
        PlatformConfig.setQQZone(ConstantOther.QQ_APPID, ConstantOther.QQ_APPKEY);
        PlatformConfig.setQQFileProvider("com.dingdingyijian.ddyj.fileprovider");
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        Logger.d("", "初始化友盟===========");
    }

    private void initMMKV() {
        MMKV.initialize(getContext());
    }

    public Context getContext() {
        return mContext == null ? mContext = getApplicationContext() : mContext;
    }

    private void initARouter() {
        if (AppConfig.isDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
    private static void initJPush(Context context) {
        /**
         * 统一推送服务标准接口
         * context:应用上下文
         * appId:在极光官网注册应用时生成的APPKEY       b9dde70546bafcc10b877e2c
         * appKey:填null即可
         * appSecret:填空即可
         * callback:该接口的结果回调，状态码为0则说明调用成功，其它值均为失败
         */
        JPushInterface.setDebugMode(AppConfig.isDebug());
        JPushUPSManager.registerToken(context, ConstantOther.JPUSH_APP_KEY, null, "", tokenResult -> {
            //callback:该接口的结果回调，状态码为0则说明调用成功，其它值均为失败
            Logger.d("", "初始化，极光注册code===========" + tokenResult.getReturnCode());
        });
        String registrationID = JPushInterface.getRegistrationID(context);
        if (!TextUtils.isEmpty(registrationID)) {
            PreferenceUtil.getInstance().commitString(ConstantOther.APP_PUSH_ID, registrationID);
            Logger.d("", "初始化，极光注册id===========" + registrationID);
        }
    }
    private void initLiveEvent() {
        LiveEventBus
                .config()
                .autoClear(true)
                .setContext(getContext())
                .enableLogger(AppConfig.isDebug());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
                return;
            } else if ((e instanceof IOException)) {
                return;
            } else if (e instanceof InterruptedException) {
                return;
            } else if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return;
            } else if (e instanceof IllegalStateException) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return;
            }
        });
    }


}
