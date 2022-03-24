package com.dingdingyijian.ddyj.utils;

import com.tencent.mmkv.MMKV;


/**
 * SharedPreferences用于数据缓存
 */
public class PreferenceUtil {
    private static PreferenceUtil instance;
    private MMKV mmkv;


    private PreferenceUtil() {
        mmkv = MMKV.defaultMMKV();
    }

    public static PreferenceUtil getInstance() {
        if (instance == null) {
            synchronized (PreferenceUtil.class) {
                if (instance == null) {
                    instance = new PreferenceUtil();
                }
            }
        }
        return instance;
    }

    public void commitString(String key, String value) {
        mmkv.encode(key, value);
    }

    public String getString(String key) {
        return mmkv.decodeString(key);
    }

    public void commitInt(String key, int value) {
        mmkv.encode(key, value);
    }

    public int getInt(String key) {
        return mmkv.decodeInt(key);
    }

    public void commitFloat(String key, float value) {
        mmkv.encode(key, value);
    }

    public float getFloat(String key) {
        return mmkv.decodeFloat(key);
    }

    public void commitLong(String key, long value) {
        mmkv.encode(key, value);
    }

    public long getLong(String key) {
        return mmkv.decodeLong(key);
    }

    public void commitBoolean(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public Boolean getBoolean(String key) {
        return mmkv.decodeBool(key);
    }


    public void removeKey(String key) {
        mmkv.removeValueForKey(key);
    }

    public void removeAll() {
        mmkv.clearAll();
    }

}
