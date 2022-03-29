package com.dingdingyijian.ddyj.mvp.data;

import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: DDYiJian
 * @time: 2022/3/29
 * @describe: com.dingdingyijian.ddyj.mvp.data
 */
public class UserUpLoad {

    /**
     * 用户上传头像
     * @param filePath
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public static MultipartBody.Builder getParameter(String filePath){
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filePath);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        requestBody.addFormDataPart("userType", "2");
        requestBody.addFormDataPart("uid", PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
        requestBody.addFormDataPart("filePath", "");
        requestBody.addFormDataPart("file", file.getName(), body);
        return requestBody;
    }
}
