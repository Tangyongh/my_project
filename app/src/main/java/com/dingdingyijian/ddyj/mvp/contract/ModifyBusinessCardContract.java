package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.widget.TextView;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.UserInfoBean;
import com.dingdingyijian.ddyj.mvp.bean.UserUpLoadBean;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @author: DDYiJian
 * @time: 2022/3/28
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface ModifyBusinessCardContract {

    /**
     * View
     */
    interface View extends BaseViewImp {
        //上传用户头像结果
        void getUploadAvatarResult(UserUpLoadBean userUpLoadBean);

        //修改用户头像
        void getModifyUserAvatarResult();

        //用户信息
        void getUserInfoResult(UserInfoBean bean);
    }

    /**
     * Presenter
     */
    abstract class Presenter extends BasePresenter<ModifyBusinessCardContract.View> {
        //上传用户头像
        public abstract void getUploadAvatar(MultipartBody.Builder requestBody);

        //修改用户头像
        public abstract void getModifyUserAvatar(HashMap<String, String> hashMap);

        //用户信息
        public abstract void getUserInfo(String uid);
        //用户信息结果展示
        public abstract void getUserInfoUI(UserInfoBean bean,ShapeableImageView userIcon, TextView tvUserLocAddress,TextView etContent,TextView tvWorkGood,TextView tvWork,TextView tvUserStatus);
    }

    interface Model {
        //上传用户头像
        void getUploadAvatar(MultipartBody.Builder requestBody, Context context, BaseModelCallBack callBack);

        //修改用户头像
        void getModifyUserAvatar(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack);

        //用户信息
        void getUserInfo(String uid, Context context, BaseModelCallBack callBack);
    }
}
