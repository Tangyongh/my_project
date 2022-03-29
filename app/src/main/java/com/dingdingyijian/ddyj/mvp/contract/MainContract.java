package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;


/**
 * @author: DDYiJian
 * @time: 2022/3/9
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface MainContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {
        //广告弹窗请求结果
        void getAdPopupResult();

        //评价弹窗
        void getCommentPopupResult();

        //未读消息
        void getNoticeNoReadResult(NoticeNoReadBean noticeNoReadBean);
    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {
        //广告弹窗
        public abstract void getAdPopup(HashMap<String, String> hashMap);

        //评价弹窗
        public abstract void getCommentPopup();

        //未读消息
        public abstract void getNoticeNoRead();

        //获取定位权限
        public abstract void getPermissions();

        //状态栏
        public abstract void initStatusBar();
    }


    /**
     * Model
     */
    interface Model {
        //广告弹窗
        void getAdPopup(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack);

        //评价弹窗
        void getCommentPopup(Context context, BaseModelCallBack callBack);

        //未读消息
        void getNoticeNoRead(Context context, BaseModelCallBack callBack);
    }
}
