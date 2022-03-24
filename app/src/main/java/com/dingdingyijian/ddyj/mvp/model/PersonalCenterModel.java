package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.api.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.contract.PersonalFragmentContract;
import com.dingdingyijian.ddyj.net.callback.BaseObserver;
import com.dingdingyijian.ddyj.net.callback.RxHelper;

/**
 * @author: tyh
 * @date: 2022/3/21 22:37
 * @description:
 */
public class PersonalCenterModel implements PersonalFragmentContract.Model {

    /**
     * 个人中心信息
     *
     * @param uid
     * @param context
     * @param callBack
     */
    @Override
    public void getUserCenter(String uid, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userCenterInfo(uid)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<UserCenterInfoBean>(context) {
                    @Override
                    public void onSuccess(UserCenterInfoBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);

                    }
                });

    }


    /**
     * 个人中心banner
     *
     * @param bannerBean
     * @param context
     * @param callBack
     */
    @Override
    public void getBanner(BannerBean bannerBean, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .banner(bannerBean)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<BannerBean>(context) {
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
     * 未读消息
     *
     * @param context
     * @param callBack
     */
    @Override
    public void getNoticeNoRead(Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .noticeNoRead()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<NoticeNoReadBean>(context) {
                    @Override
                    public void onSuccess(NoticeNoReadBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onError(errorMsg);

                    }
                });
    }

    /**
     * 个人中心推荐分享
     *
     * @param context
     * @param callBack
     */
    @Override
    public void getUserShare(Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userCenterShare()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<String>(context) {
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
}
