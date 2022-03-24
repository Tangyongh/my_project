package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.contract.PersonalFragmentContract;
import com.dingdingyijian.ddyj.mvp.model.PersonalCenterModel;
import com.dingdingyijian.ddyj.net.callback.RxHelper;
import com.dingdingyijian.ddyj.utils.ToastUtil;

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
                ToastUtil.showMsg(errorMsg);
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
                ToastUtil.showMsg(errorMsg);
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
                ToastUtil.showMsg(errorMsg);
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
                ToastUtil.showMsg(errorMsg);
            }
        });
    }
}
