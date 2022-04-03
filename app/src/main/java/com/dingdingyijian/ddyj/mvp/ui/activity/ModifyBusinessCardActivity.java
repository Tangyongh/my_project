package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityModifyBusinessCardBinding;
import com.dingdingyijian.ddyj.event.RefreshEvent;
import com.dingdingyijian.ddyj.glide.GlideApp;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.bean.UserInfoBean;
import com.dingdingyijian.ddyj.mvp.bean.UserUpLoadBean;
import com.dingdingyijian.ddyj.mvp.contract.ModifyBusinessCardContract;
import com.dingdingyijian.ddyj.mvp.data.UserUpLoad;
import com.dingdingyijian.ddyj.mvp.presenter.ModifyBusinessCardPresenter;
import com.dingdingyijian.ddyj.pictureSelector.PictureSelectorUtils;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author: DDYiJian
 * @time: 2022/3/28
 * @describe: 编辑名片
 */
@Route(path = Constant.PATH_MODIFY_CARD)
public class ModifyBusinessCardActivity extends BaseActivity<ModifyBusinessCardContract.View,
        ModifyBusinessCardContract.Presenter, ActivityModifyBusinessCardBinding> implements ModifyBusinessCardContract.View {

    private String mImagePath; //上传图片路径
    private boolean isUpLoadAvatar = false; //是否上传过头像，关闭页面的时候刷新下个人中心接口

    @Override
    public ModifyBusinessCardContract.Presenter createPresenter() {
        return new ModifyBusinessCardPresenter(mContext);
    }

    @Override
    public ModifyBusinessCardContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getBinding().toolbar.tvTitleCenterName.setText("编辑名片");
        onClickListener();
        //用户信息
        getPresenter().getUserInfo(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
    }


    //上传头像
    @Override
    public void getUploadAvatarResult(UserUpLoadBean userUpLoadBean) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("avatarUrl", userUpLoadBean.getUrl());
        hashMap.put("avatarId", userUpLoadBean.getFileId());
        //修改头像
        getPresenter().getModifyUserAvatar(hashMap);
    }

    @Override
    public void getModifyUserAvatarResult() {
        isUpLoadAvatar = true;
        //用户信息
        getPresenter().getUserInfo(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
    }

    //用户信息请求结果
    @Override
    public void getUserInfoResult(UserInfoBean bean) {
        if (bean == null) return;
        getPresenter().getUserInfoUI(bean, getBinding().userIcon, getBinding().tvUserLocAddress, getBinding().etContent,
                getBinding().tvWorkGood, getBinding().tvWork, getBinding().tvUserStatus);
    }


    //图片上传之后的结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                for (LocalMedia media : selectList) {
                    if (media.isCompressed()) {
                        mImagePath = media.isCompressed() ? media.getCompressPath() : media.getPath();
                    }
                }
                //上传压缩之后的图片
                MultipartBody.Builder builder = UserUpLoad.getParameter(mImagePath);
                getPresenter().getUploadAvatar(builder);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onEvent();
    }

    //发送消息，刷新主页接口
    private void onEvent() {
        if (isUpLoadAvatar) {
            LiveEventBus.get(RefreshEvent.class).post(new RefreshEvent());
        }
    }

    //点击事件
    private void onClickListener() {
        //相册选择或拍照
        getBinding().userIcon.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                PictureSelectorUtils.getInstance(mContext).PictureSelector(ModifyBusinessCardActivity.this,
                        1, 1, 4);
            }
        });
        //关闭页面
        getBinding().toolbar.btnBack.setOnClickListener(v -> {
            finish();
            onEvent();
        });
    }
}
