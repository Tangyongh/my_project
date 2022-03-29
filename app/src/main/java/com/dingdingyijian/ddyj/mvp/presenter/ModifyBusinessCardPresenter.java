package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.bean.UserInfoBean;
import com.dingdingyijian.ddyj.mvp.bean.UserUpLoadBean;
import com.dingdingyijian.ddyj.mvp.contract.ModifyBusinessCardContract;
import com.dingdingyijian.ddyj.mvp.model.ModifyBusinessCardModel;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @author: DDYiJian
 * @time: 2022/3/29
 * @describe: com.dingdingyijian.ddyj.mvp.presenter
 */
public class ModifyBusinessCardPresenter extends ModifyBusinessCardContract.Presenter {
    private Context mContext;
    private ModifyBusinessCardModel mModel;

    public ModifyBusinessCardPresenter(Context context) {
        this.mContext = context;
        this.mModel = new ModifyBusinessCardModel();
    }

    //上传头像
    @Override
    public void getUploadAvatar(MultipartBody.Builder requestBody) {
        mModel.getUploadAvatar(requestBody, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getUploadAvatarResult((UserUpLoadBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //修改头像
    @Override
    public void getModifyUserAvatar(HashMap<String, String> hashMap) {
        mModel.getModifyUserAvatar(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getModifyUserAvatarResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //用户信息
    @Override
    public void getUserInfo(String uid) {
        mModel.getUserInfo(uid, mContext, new BaseModelCallBack() {
            @Override
            public void onNext(Object result) {
                getView().getUserInfoResult((UserInfoBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //用户信息结果
    @Override
    public void getUserInfoUI(UserInfoBean bean, ShapeableImageView userIcon, TextView tvUserLocAddress, TextView etContent, TextView tvWorkGood, TextView tvWork, TextView tvUserStatus) {
        //用户头像
        GlideImage.getInstance().loadImage(mContext, bean.getDdUser().getAvatarUrl(), R.mipmap.user_shape_icon, userIcon);
        //用户注册地址
        tvUserLocAddress.setText(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_MY_ADDRESS));
        //自我描述
        etContent.setText(bean.getDdUser().getIntroduction());
        String goodAtCategory = bean.getUserCategoryInfo().getGoodAtCategory();
        tvWorkGood.setText(TextUtils.isEmpty(goodAtCategory) ? "请选择" : goodAtCategory);
        UserInfoBean.UserCategoryInfoBean userCategoryInfo = bean.getUserCategoryInfo();
        if (userCategoryInfo != null) {
            String canCategory = userCategoryInfo.getCanCategory();
            String category = "";
            String str = "";
            if (TextUtils.isEmpty(canCategory)) {
                tvWork.setText("请选择");
            } else {
                String[] split = canCategory.split(",");
                for (int i = 0; i < split.length; i++) {
                    //数据叠加且用空格替换
                    category += split[i] + "/";
                }
                if (category.length() > 0) {
                    str = category.substring(0, category.length() - 1);
                }
                tvWork.setText(str);
            }
        }
        //用户是否实名认证
        switch (bean.getDdUser().getIdcardVerify()) {
            case "0":
                tvUserStatus.setText("未认证");
                break;
            case "1":
                tvUserStatus.setText("审核中");
                break;
            case "2":
                tvUserStatus.setText("已认证");
                break;
            case "3":
                tvUserStatus.setText("认证失败");
                break;
        }
    }
}
