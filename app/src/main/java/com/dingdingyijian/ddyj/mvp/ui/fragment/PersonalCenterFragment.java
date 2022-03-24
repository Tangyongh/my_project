package com.dingdingyijian.ddyj.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseFragment;
import com.dingdingyijian.ddyj.databinding.FragmentPersonalBinding;
import com.dingdingyijian.ddyj.event.LoginEvent;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.data.DataInfoResult;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.contract.PersonalFragmentContract;
import com.dingdingyijian.ddyj.mvp.presenter.PersonalFragmentPresenter;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.LoginUtils;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment<PersonalFragmentContract.View, PersonalFragmentContract.Presenter, FragmentPersonalBinding> implements PersonalFragmentContract.View {


    public static PersonalCenterFragment getInstance() {
        return new PersonalCenterFragment();
    }

    @Override
    public PersonalFragmentContract.Presenter createPresenter() {
        return new PersonalFragmentPresenter(mContext);
    }

    @Override
    public PersonalFragmentContract.View createView() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //用户信息
        refreshUserInfo();
        //banner
        setBanner();
        //消息监听
        onEvent();
    }


    //登录成功或者退出登录的时候刷新下接口
    private void onEvent() {
        LiveEventBus.get(LoginEvent.class).observe(this, loginEvent -> {
            //用户信息
            refreshUserInfo();
        });
    }

    //banner图
    private void setBanner() {
        //请求banner
        BannerBean bannerBean = new BannerBean();
        String cityCode = PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE);
        bannerBean.setType("9");
        bannerBean.setCityId(TextUtils.isEmpty(cityCode) ? "1234567" : cityCode);
        getPresenter().getBanner(bannerBean);
    }

    //个人中心
    @SuppressLint("SetTextI18n")
    @Override
    public void getUserCenterResult(UserCenterInfoBean bean) {
        if (bean == null) return;
        PreferenceUtil.getInstance().commitString(ConstantOther.IDCARDVERIFY, bean.getIdcardVerify() + "");
        PreferenceUtil.getInstance().commitString(ConstantOther.REAL_NAME, bean.getRealName());
        PreferenceUtil.getInstance().commitString(ConstantOther.IDCARRDNO, bean.getIdcardNo());
        PreferenceUtil.getInstance().commitString(ConstantOther.IS_SET_LOGIN_NAME, bean.getUserName());
        PreferenceUtil.getInstance().commitString(ConstantOther.ACCEPT_DISTANCE, bean.getAcceptDistance());
        PreferenceUtil.getInstance().commitString(ConstantOther.USER_ICON, bean.getAvatarUrl());
        PreferenceUtil.getInstance().commitString(ConstantOther.IS_VIP, bean.getVipLevel());
        //用戶头像
        GlideImage.getInstance().loadImage(mContext, bean.getAvatarUrl(), R.mipmap.user_shape_icon, getBinding().userImage);
        PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_USER_ICON, bean.getAvatarUrl());
        //用户名
        if ("2".equals(bean.getIdcardVerify()) || "4".equals(bean.getIdcardVerify())) {
            getBinding().tvUserName.setText(bean.getRealName());
        } else {
            getBinding().tvUserName.setText(ComUtil.getMobile(bean.getMobile()));
        }
        //区分普通会员和高级会员
        String vipLevel = bean.getVipLevel();
        getBinding().ivUserFlag.setImageResource("0".equals(vipLevel) ? R.mipmap.icon_vip_no : R.mipmap.icon_vip);
        if (bean.isPledgeMoney()) {
            getBinding().tvUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.icon_bzj), null);
        } else {
            getBinding().tvUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        UserCenterInfoBean.CreditUserModelBean creditUserModel = bean.getCreditUserModel();
        if (creditUserModel != null) {
            getBinding().contentCredit.setVisibility(View.VISIBLE);
            getBinding().tvCreditScale.setText("信用积分:" + ComUtil.getFormatValue(creditUserModel.getCreditScore()) + "分");
        }

    }

    @Override
    public void getBannerResult(BannerBean bannerBean) {
        //banner请求数据
        DataInfoResult.getBannerResult(getBinding().banner, bannerBean);
    }

    @Override
    public void getNoticeNoReadResult(NoticeNoReadBean noticeNoReadBean) {

    }

    @Override
    public void getUserShareResult() {

    }

    //个人中心用户信息
    private void refreshUserInfo() {
        if (LoginUtils.isLogin(mContext)) {
            getPresenter().getUserCenter(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
            getBinding().content.setVisibility(View.VISIBLE);
            getBinding().btnLogin.setVisibility(View.GONE);
            getBinding().ivUserFlag.setVisibility(View.VISIBLE);
            getBinding().userImage.setClickable(true);
        } else {
            //未登录的时候显示
            getBinding().content.setVisibility(View.GONE);
            getBinding().btnLogin.setVisibility(View.VISIBLE);
            getBinding().ivUserFlag.setVisibility(View.GONE);
            getBinding().userImage.setClickable(false);
            getBinding().btnLogin.setOnClickListener(v -> ARouter.getInstance().build(Constant.PATH_LOGIN_PWD).navigation());
            GlideImage.getInstance().loadImage(mContext, R.mipmap.user_shape_icon, 0, getBinding().userImage);
        }
    }
}
