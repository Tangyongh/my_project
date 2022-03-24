package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface PersonalFragmentContract {

    /**
     * View
     */
    interface View extends BaseViewImp {
        //个人中心
        void getUserCenterResult(UserCenterInfoBean userCenterInfoBean);

        //banner请求结果
        void getBannerResult(BannerBean bannerBean);

        //未读消息
        void getNoticeNoReadResult(NoticeNoReadBean noticeNoReadBean);

        //推荐分享
        void getUserShareResult();
    }


    /**
     * Presenter
     */
    abstract class Presenter extends BasePresenter<View> {
        //个人中心
        public abstract void getUserCenter(String uid);

        //banner请求
        public abstract void getBanner(BannerBean bannerBean);

        //未读消息
        public abstract void getNoticeNoRead();

        //推荐分享
        public abstract void getUserShare();
    }


    /**
     * Model
     */
    interface Model {
        //个人中心
        void getUserCenter(String uid, Context context, BaseModelCallBack callBack);

        //banner
        void getBanner(BannerBean bannerBean, Context context, BaseModelCallBack callBack);

        //未读消息
        void getNoticeNoRead(Context context, BaseModelCallBack callBack);

        //推荐分享
        void getUserShare(Context context, BaseModelCallBack callBack);
    }

}
