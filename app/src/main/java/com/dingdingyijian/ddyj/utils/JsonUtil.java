package com.dingdingyijian.ddyj.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {


    private Gson gson = null;
    private static JsonUtil instance;


    private JsonUtil() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient() // json宽松
                    .setDateFormat("yyyy-MM-dd HH:mm:ss") //时间转化为特定格式
                    .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                    .setPrettyPrinting() //对结果进行格式化，增加换行
                    .disableHtmlEscaping() //防止特殊字符出现乱码
                    .create();
        }
    }

    public static JsonUtil getInstance() {
        if (instance == null) {
            synchronized (JsonUtil.class) {
                if (instance == null) {
                    instance = new JsonUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 将json转化为JavaBean对象
     *
     * @return
     */

    public <T> T fromJson(String response, Class<T> clazz) {
        T result = null;
        try {
            result = gson.fromJson(response, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把一个map变成json字符串
     *
     * @param map
     * @return
     */
    public String parseToJson(Map<String, String> map) {
        try {
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 把json字符串变成map
     *
     * @param json
     * @return
     */
    public HashMap<String, Object> parseJsonToMap(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

}
