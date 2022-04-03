package com.dingdingyijian.ddyj.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommonUtils
 */
public class ComUtil {

    private static long lastClickTime;

    //处理重复点击事件
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 改变状态栏字体颜色
      * @param context
     * @param isBlack true黑色  false白色
     */
    public static void changeStatusBarTextColor(Context context,boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //恢复状态栏白色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            }
        }
    }


    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8|9][0-9]{9}$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //long型时间转换为字符串时间类型
    public static String longToString(Object longTime, String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat == null ? "yyyy-MM-dd" : timeFormat);
        long time = 0;
        if (longTime instanceof Integer || longTime instanceof Long) {
            return formatter.format(longTime);
        } else if (longTime instanceof String) {
            return formatter.format(Long.valueOf((String) longTime));
        }
        return "时间获取错误";
    }

    /**
     * 是否有网络连接，不管是wifi还是数据流量
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }

    // 屏幕宽度（像素）
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static String getMd5Str(HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            sb.append(entry.getValue().toString());
        }
        return MD5Utils.getMd5(sb.toString() );
    }

    public static HashMap<String, String> getMd5Str(String[] keyArray, String[] valueArray) {
        HashMap<String, String> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        if (keyArray.length != valueArray.length) {
        } else {
            for (int i = 0; i < keyArray.length; i++) {
                map.put(keyArray[i], valueArray[i]);
            }
            for (int i = 0; i < valueArray.length; i++) {
                sb.append(valueArray[i]);
            }
            map.put("secret", MD5Utils.getMd5(sb.toString()));
            return map;
        }
        return null;
    }

    //判断Activity是否Destroy
    public static boolean isDestroy(RxAppCompatActivity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 首中字符正常显示，后面的星号代替
     */
    public static String getHideUserName(String userName) {
        try {
            if (userName == null) return "未绑定";

            int length = userName.length();

            if (length > 1) {
                String startNum = userName.substring(0, 1);
                String endNum = userName.substring(length - 1, length);
                userName = startNum + "**";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }
    public static String getMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //判断是否安装了微信
    public static boolean isWeixinAvilible(Context context) {
        IWXAPI mWXApi = WXAPIFactory.createWXAPI(context, ConstantOther.WX_APPID, true);
        if(mWXApi.isWXAppInstalled()){
            return true;
        }
        return false;
    }

    /**
     * 富文本适配
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public static String getFormatValue(double value) {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(value);
    }
}
