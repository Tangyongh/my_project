package com.dingdingyijian.ddyj.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseFragment;
import com.dingdingyijian.ddyj.databinding.FragmentPersonalBinding;
import com.dingdingyijian.ddyj.event.RefreshEvent;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.contract.PersonalFragmentContract;
import com.dingdingyijian.ddyj.mvp.data.DataInfoResult;
import com.dingdingyijian.ddyj.mvp.presenter.PersonalFragmentPresenter;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.net.helper.RxHelper;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.LoginUtils;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.ibd.tablayout.utils.UnreadMsgUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;

import okhttp3.MultipartBody;


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
        LiveEventBus.get(RefreshEvent.class).observe(this, loginEvent -> {
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
        getPresenter().setUserInfo(bean, getBinding().tvUserName, getBinding().ivUserFlag,
                getBinding().userImage, getBinding().tvCreditScale, getBinding().contentCredit);
    }

    @Override
    public void getBannerResult(BannerBean bannerBean) {
        //banner请求数据
        DataInfoResult.getBannerResult(getBinding().banner, bannerBean);
    }

    //未读消息
    @Override
    public void getNoticeNoReadResult(NoticeNoReadBean noticeNoReadBean) {
        if (noticeNoReadBean == null) return;
        int noReadNum = noticeNoReadBean.getNoReadNum();
        if (noReadNum == 0) {
            getBinding().tvMessageRead.setVisibility(View.GONE);
        }
        if (noReadNum > 0 && noReadNum < 100) {
            UnreadMsgUtils.show(getBinding().tvMessageRead, noReadNum);
        }
        if (noReadNum > 99) {
            UnreadMsgUtils.show(getBinding().tvMessageRead, 99);
        }
    }

    @Override
    public void getUserShareResult() {

    }

    //个人中心用户信息
    private void refreshUserInfo() {
        if (LoginUtils.isLogin()) {
            getPresenter().getUserCenter(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
            getPresenter().getNoticeNoRead();//未读消息接口
            getBinding().content.setVisibility(View.VISIBLE);
            getBinding().btnLogin.setVisibility(View.GONE);
            getBinding().ivUserFlag.setVisibility(View.VISIBLE);
            getBinding().userImage.setClickable(true);
        } else {
            //未登录的时候显示
            getBinding().tvMessageRead.setVisibility(View.GONE);
            getBinding().content.setVisibility(View.GONE);
            getBinding().btnLogin.setVisibility(View.VISIBLE);
            getBinding().ivUserFlag.setVisibility(View.GONE);
            getBinding().userImage.setClickable(false);
            getBinding().btnLogin.setOnClickListener(v -> ARouter.getInstance().build(Constant.PATH_LOGIN_PWD).navigation());
            GlideImage.getInstance().loadImage(mContext, R.mipmap.user_shape_icon, 0, getBinding().userImage);
        }
    }
}
