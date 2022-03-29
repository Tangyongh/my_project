package com.dingdingyijian.ddyj.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.contract.PersonalFragmentContract;
import com.dingdingyijian.ddyj.mvp.model.PersonalCenterModel;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLRelativeLayout;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.presenter
 */
public class PersonalFragmentPresenter extends PersonalFragmentContract.Presenter {

    private Context mContext;
    private PersonalCenterModel mModel;

    public PersonalFragmentPresenter(Context context) {
        this.mContext = context;
        this.mModel = new PersonalCenterModel();
    }


    //个人中心用户信息
    @Override
    public void getUserCenter(String uid) {
        mModel.getUserCenter(uid, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getUserCenterResult((UserCenterInfoBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });

    }

    //个人中心banner
    @Override
    public void getBanner(BannerBean bannerBean) {
        mModel.getBanner(bannerBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getBannerResult((BannerBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //未读消息
    @Override
    public void getNoticeNoRead() {
        mModel.getNoticeNoRead( mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getNoticeNoReadResult((NoticeNoReadBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //推荐分享
    @Override
    public void getUserShare() {
        mModel.getUserShare(mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getUserShareResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //用户信息结果
    @SuppressLint("SetTextI18n")
    @Override
    public void setUserInfo(UserCenterInfoBean bean,
                            TextView tvUserName, ImageView ivUserFlag, ImageView userImage, TextView tvCreditScale, BLRelativeLayout contentCredit) {
        PreferenceUtil.getInstance().commitString(ConstantOther.IDCARDVERIFY, bean.getIdcardVerify() + "");
        PreferenceUtil.getInstance().commitString(ConstantOther.REAL_NAME, bean.getRealName());
        PreferenceUtil.getInstance().commitString(ConstantOther.IDCARRDNO, bean.getIdcardNo());
        PreferenceUtil.getInstance().commitString(ConstantOther.IS_SET_LOGIN_NAME, bean.getUserName());
        PreferenceUtil.getInstance().commitString(ConstantOther.ACCEPT_DISTANCE, bean.getAcceptDistance());
        PreferenceUtil.getInstance().commitString(ConstantOther.USER_ICON, bean.getAvatarUrl());
        PreferenceUtil.getInstance().commitString(ConstantOther.IS_VIP, bean.getVipLevel());
        //用戶头像
        GlideImage.getInstance().loadImage(mContext, bean.getAvatarUrl(), R.mipmap.user_shape_icon, userImage);
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_ICON, bean.getAvatarUrl());
        //用户名
        if ("2".equals(bean.getIdcardVerify()) || "4".equals(bean.getIdcardVerify())) {
            tvUserName.setText(bean.getRealName());
        } else {
            tvUserName.setText(ComUtil.getMobile(bean.getMobile()));
        }
        //区分普通会员和高级会员
        String vipLevel = bean.getVipLevel();
        ivUserFlag.setImageResource("0".equals(vipLevel) ? R.mipmap.icon_vip_no : R.mipmap.icon_vip);
        if (bean.isPledgeMoney()) {
           tvUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.icon_bzj), null);
        } else {
            tvUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        UserCenterInfoBean.CreditUserModelBean creditUserModel = bean.getCreditUserModel();
        if (creditUserModel != null) {
            contentCredit.setVisibility(View.VISIBLE);
            tvCreditScale.setText("信用积分:" + ComUtil.getFormatValue(creditUserModel.getCreditScore()) + "分");
        }
    }
}
