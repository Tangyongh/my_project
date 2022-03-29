package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.UserInfoBean;
import com.dingdingyijian.ddyj.mvp.bean.UserUpLoadBean;
import com.dingdingyijian.ddyj.mvp.contract.ModifyBusinessCardContract;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.net.helper.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @author: DDYiJian
 * @time: 2022/3/29
 * @describe: com.dingdingyijian.ddyj.mvp.model
 */
@SuppressWarnings("all")
public class ModifyBusinessCardModel implements ModifyBusinessCardContract.Model {

    //上传头像
    @Override
    public void getUploadAvatar(MultipartBody.Builder requestBody, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .upLoadAvatar(requestBody.build())
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<UserUpLoadBean>(context) {
                    @Override
                    public void onSuccess(UserUpLoadBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }
                });
    }

    //修改头像
    @Override
    public void getModifyUserAvatar(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .modifyUserAvatar(ConstantUtils.convertMapToBody(hashMap))
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<String>(context,false) {
                    @Override
                    public void onSuccess(String result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }
                });
    }

    //用户信息
    @Override
    public void getUserInfo(String uid, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userInfo(uid)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<UserInfoBean>(context,false) {
                    @Override
                    public void onSuccess(UserInfoBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }
                });
    }
}
