package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.event.LoginEvent;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.dingdingyijian.ddyj.mvp.contract.HomeFragmentContract;
import com.dingdingyijian.ddyj.mvp.model.HomeFragmentModel;
import com.dingdingyijian.ddyj.net.callback.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.LoginUtils;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.HashMap;
import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: 首页
 * <p>
 * 为什么rx绑定生命周期不放在model层，
 * 因为我们不希望m层拥有直接操作v层的权利 隔离v与m层
 * 只希望m层与p层交互
 */
public class HomeFragmentPresenter extends HomeFragmentContract.Presenter {

    private Context mContext;
    private HomeFragmentModel model;

    public HomeFragmentPresenter(Context context) {
        this.mContext = context;
        this.model = new HomeFragmentModel();
    }


    /**
     * 首页banner
     *
     * @param bannerBean
     */

    @Override
    public void getBanner(BannerBean bannerBean) {
        model.getBanner(bannerBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getBannerResult((BannerBean) result);

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }


    /**
     * 接单滚动信息
     *
     * @param acceptListBean
     */
    @Override
    public void getHomeNeedsNotice(NeedsAcceptListBean acceptListBean) {

        model.getHomeNeedsNotice(acceptListBean, mContext, new BaseModelCallBack<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onNext(Object result) {
                getView().getHomeNeedsNoticeResult((List<NeedsAcceptListBean>) result);
            }

            @Override
            public void onError(String e) {
                ToastUtil.showMsg(e);
            }
        });
    }

    /**
     * 首页地图上的头像
     *
     * @param userIconBean
     */
    @Override
    public void getMapViewIcon(UserIconBean userIconBean) {
        model.getMapViewIcon(userIconBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getMapViewIconResult((List<UserIconBean>) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }


    /**
     * 待处理信息
     *
     * @param uid
     */
    @Override
    public void getNeedsCount(String uid) {
        model.getNeedsCount(uid, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getNeedsCountResult((NeedsCountBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }

    @Override
    public void onEvent(TextView missNeeds, TextView myNeeds, TextView sendNeeds) {
        LiveEventBus.get(LoginEvent.class).observe((LifecycleOwner) mContext, loginEvent -> {
            //待处理信息
            setNeedsCount(missNeeds, myNeeds, sendNeeds);
        });
    }

    @Override
    public void setNeedsCount(TextView missNeeds, TextView myNeeds, TextView sendNeeds) {
        boolean isLogin = LoginUtils.isLogin(mContext);
        //判断是否登录
        if (isLogin) {
            getNeedsCount(PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_USER_ID));
        } else {
            missNeeds.setText("暂未登录，");
            myNeeds.setText("登录查看我的订单");
            sendNeeds.setText("");
        }
    }

    //设置banner
    @Override
    public void setBanner() {
        //请求banner
        BannerBean bannerBean = new BannerBean();
        String cityCode = PreferenceUtil.getInstance().getString(ConstantOther.KEY_APP_ADCODE);
        bannerBean.setType("4");
        bannerBean.setCityId(TextUtils.isEmpty(cityCode) ? "1234567" : cityCode);
        getBanner(bannerBean);
    }
}
