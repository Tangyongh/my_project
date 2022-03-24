package com.dingdingyijian.ddyj;


/**
 * APP配置参数
 */
public class AppConfig {


    /**
     * 当前是否为调试模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前构建的模式
     */
    public static String getBuildType() {
        return BuildConfig.BUILD_TYPE;
    }


    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }


    /**
     * 主要服务器主机地址
     */
    public static String getHostUrl() {
        return BuildConfig.HOST_URL_MAIN;
    }

    /**
     * 企业服务器主机地址
     *
     * @return
     */
    public static String getCompanyHostUrl() {
        return BuildConfig.HOST_URL_COMPANY;
    }

    /**
     * 工到服务器主机地址
     *
     * @return
     */
    public static String getMajorHostUrl() {
        return BuildConfig.HOST_URL_MAJOR;
    }


}
