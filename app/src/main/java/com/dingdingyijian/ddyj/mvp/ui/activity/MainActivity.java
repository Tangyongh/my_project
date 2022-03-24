package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.adapter.ViewPagerAdapter;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityMainBinding;
import com.dingdingyijian.ddyj.event.LoginEvent;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.contract.MainContract;
import com.dingdingyijian.ddyj.mvp.presenter.MainPresenter;
import com.dingdingyijian.ddyj.mvp.ui.fragment.CategoryFragment;
import com.dingdingyijian.ddyj.mvp.ui.fragment.HomeFragment;
import com.dingdingyijian.ddyj.mvp.ui.fragment.PersonalCenterFragment;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.LoginUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.ibd.tablayout.listener.OnTabSelectListener;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;


/**
 * 主页
 */
@Route(path = Constant.PATH_MAIN)
public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter, ActivityMainBinding> implements MainContract.View, OnTabSelectListener {
    private ArrayList<Fragment> mFragment = new ArrayList<>();


    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(mContext);
    }

    @Override
    public MainContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //状态栏
        getPresenter().initStatusBar(mContext);
        //初始化tab
        initTab();
        initData();
    }

    private void initData() {
        //请求定位权限
        getPresenter().getPermissions(mContext);
        refreshNoticeNoRead();
        //消息监听
        onEvent();
    }

    //初始化tab
    private void initTab() {
        mFragment.add(HomeFragment.getInstance()); //找人
        mFragment.add(HomeFragment.getInstance()); //货运
        mFragment.add(CategoryFragment.getInstance()); //机械出租
        mFragment.add(PersonalCenterFragment.getInstance()); //个人中心
        getBinding().viewPage.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragment));
        getBinding().viewPage.setOffscreenPageLimit(4);
        getBinding().tabLayout.setViewPager(getBinding().viewPage);
        getBinding().tabLayout.setCurrentTab(0);
        getBinding().viewPage.setCurrentItem(0);
        getBinding().tabLayout.setOnTabSelectListener(this);
    }

    //消息回调 (此处就是接收到登录或者退出登录做出的操作)
    private void onEvent() {
        LiveEventBus.get(LoginEvent.class).observe(this, loginEvent -> {
            //回调刷新接口
            refreshNoticeNoRead();
        });
    }

    //未读消息
    private void refreshNoticeNoRead() {
        //已登录
        if (LoginUtils.isLogin(mContext)) {
            getPresenter().getNoticeNoRead();
        } else {
            //隐藏未读消息
            getBinding().tabLayout.hideMsg(3);
        }
    }


    @Override
    public void onTabSelect(int position) {


    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void getAdPopupResult() {

    }

    @Override
    public void getCommentPopupResult() {

    }

    //未读消息
    @Override
    public void getNoticeNoReadResult(NoticeNoReadBean noticeNoReadBean) {
        if (noticeNoReadBean == null) return;
        //未读消息数量
        int noReadNum = noticeNoReadBean.getNoReadNum();
        if (noReadNum == 0) {
            getBinding().tabLayout.hideMsg(3);
        }
        if (noReadNum > 0 && noReadNum < 100) {
            getBinding().tabLayout.showMsg(3, noReadNum);
        }
        if (noReadNum > 99) {
            getBinding().tabLayout.showMsg(3, 99);
        }
    }
}