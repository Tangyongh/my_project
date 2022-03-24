package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.widget.TextView;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface HomeFragmentContract {

    /**
     * View
     */
    interface View extends BaseViewImp {
        //banner请求结果
        void getBannerResult(BannerBean bannerBean);

        //接单滚动信息结果
        void getHomeNeedsNoticeResult(List<NeedsAcceptListBean> acceptListBean);

        //待处理工单信息
        void getNeedsCountResult(NeedsCountBean needsCountBean);

        //首页地图上的头像
        void getMapViewIconResult(List<UserIconBean> userIconBean);

    }

    /**
     * Presenter
     */
    abstract class Presenter extends BasePresenter<View> {
        //banner请求
        public abstract void getBanner(BannerBean bannerBean);

        //接单滚动信息请求
        public abstract void getHomeNeedsNotice(NeedsAcceptListBean acceptListBean);

        //首页地图上的头像
        public abstract void getMapViewIcon(UserIconBean userIconBean);

        //请求待处理
        public abstract void getNeedsCount(String uid);

        //处理消息回调
        public abstract void onEvent(TextView missNeeds, TextView myNeeds, TextView sendNeeds);

        //调用 待处理信息
        public abstract void setNeedsCount(TextView missNeeds, TextView myNeeds, TextView sendNeeds);

        //设置banner 数据
        public abstract void setBanner();
    }

    /**
     * Model
     */
    @SuppressWarnings("rawtypes")
    interface Model {
        //接单滚动信息请求
        void getHomeNeedsNotice(NeedsAcceptListBean acceptListBean, Context context, BaseModelCallBack callBack);

        //banner
        void getBanner(BannerBean bannerBean, Context context, BaseModelCallBack callBack);

        //待处理
        void getNeedsCount(String uid, Context context, BaseModelCallBack callBack);

        //首页地图上的头像
        void getMapViewIcon( UserIconBean userIconBean, Context context, BaseModelCallBack callBack);
    }
}
