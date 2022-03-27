package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.dingdingyijian.ddyj.mvp.contract.HomeFragmentContract;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.helper.RxHelper;

import java.util.List;

/**
 * 首页model
 */
@SuppressWarnings("all")
public class HomeFragmentModel implements HomeFragmentContract.Model {


    /**
     * 接单滚动信息
     *
     * @param acceptListBean
     * @param context
     * @param callBack
     */
    @Override
    public void getHomeNeedsNotice(NeedsAcceptListBean acceptListBean, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .homeNeedsNotice(acceptListBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<List<NeedsAcceptListBean>>(context, false) {


                    @Override
                    public void onSuccess(List<NeedsAcceptListBean> result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }

                });
    }

    /**
     * banner图
     *
     * @param bannerBean
     * @param context
     * @param callBack
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void getBanner(BannerBean bannerBean, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .banner(bannerBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<BannerBean>(context, false) {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(BannerBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }


                });
    }


    /**
     * 待处理信息
     *
     * @param uid
     * @param context
     * @param callBack
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void getNeedsCount(String uid, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .needsCount(uid)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<NeedsCountBean>(context, false) {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(NeedsCountBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }

                });

    }

    /**
     * 首页地图上的头像
     *
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getMapViewIcon(UserIconBean userIconBean,
                               Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userHomeMapViewIcon(userIconBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<List<UserIconBean>>(context, false) {
                    @Override
                    public void onSuccess(List<UserIconBean> result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);
                    }
                });
    }
}
