package com.dingdingyijian.ddyj.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.dingdingyijian.ddyj.AppConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 基础常量工具类
 * 获取工程ApplicationContext对象时强制使用 如下getAPPContext()方法获取
 */
public class ConstantUtils {
    private static Context context;

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ConstantUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getAPPContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        String packageName = context.getPackageName();
        if (packageName == null || packageName.trim().length() == 0)
            return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    @SuppressWarnings("AccessStaticViaInstance")
    public static boolean getVersionCode() {
        boolean isFirstIn = false;
        int lastVersionCode = PreferenceUtil.getInstance().getInt("version");
        int versionCode_now = AppConfig.getVersionCode();
        if (versionCode_now > lastVersionCode) {
            PreferenceUtil.getInstance().commitInt("version", versionCode_now);
            isFirstIn = true;
        }
        return isFirstIn;
    }

    /**
     * 将map数据转换为 普通的 json RequestBody
     *
     * @param map 以前的请求参数
     * @return
     */
    public static RequestBody convertMapToBody(HashMap<String, String> map) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(map).toString());
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    @SuppressWarnings("unchecked")
    public static boolean isMobile(String str) {

        if (str == null || str.length() != 11) {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    public static void jumCall(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "400-6965-978");
        intent.setData(data);
        context.startActivity(intent);
    }

}
