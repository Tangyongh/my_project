package com.dingdingyijian.ddyj.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dingdingyijian.ddyj.utils.ComUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/04/03
 *    desc   : 友盟客户端
 */
public final class UmengClient {


    /**
     * 分享
     *
     * @param activity              Activity对象
     * @param platform              分享平台
     * @param data                  分享内容
     * @param listener              分享监听
     */
    public static void share(Activity activity, Platform platform, UmengShare.ShareData data, UmengShare.OnShareListener listener) {
        if (isAppInstalled(activity, platform.getPackageName())) {
            new ShareAction(activity)
                    .setPlatform(platform.getThirdParty())
                    .withMedia(data.create())
                    .setCallback(listener != null ? new UmengShare.ShareListenerWrapper(platform.getThirdParty(), listener) : null)
                    .share();
            return;
        }
        // 当分享的平台软件可能没有被安装的时候
        if (listener != null) {
            listener.onError(platform, new PackageManager.NameNotFoundException("您还未安装此应用"));
        }
    }

    /**
     * 登录
     *
     * @param activity              Activity对象
     * @param platform              登录平台
     * @param listener              登录监听
     */
    public static void login(Activity activity, Platform platform, UmengLogin.OnLoginListener listener) {
        if (ComUtil.isWeixinAvilible(activity));
        // 当登录的平台软件可能没有被安装的时候
       /* if (listener != null) {
            listener.onError(platform, new PackageManager.NameNotFoundException("您还未安装此应用"));
        }*/
    }

    /**
     * 设置回调
     */
    public static void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 判断 App 是否安装
     */
    public static boolean isAppInstalled(Context context, Platform platform) {
        return isAppInstalled(context, platform.getPackageName());
    }

    private static boolean isAppInstalled(Context context, @NonNull final String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}