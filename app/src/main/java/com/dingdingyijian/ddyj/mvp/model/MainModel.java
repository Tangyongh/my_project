package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.net.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.contract.MainContract;
import com.dingdingyijian.ddyj.net.BaseObserver;
import com.dingdingyijian.ddyj.net.helper.RxHelper;
import com.dingdingyijian.ddyj.utils.ConstantUtils;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/22
 * @describe: com.dingdingyijian.ddyj.mvp.model
 */
@SuppressWarnings("all")
public class MainModel implements MainContract.Model {

    /**
     * 主页广告弹窗
     *
     * @param transformer
     * @param hashMap
     * @param context
     * @param callBack
     */
    @Override
    public void getAdPopup(HashMap<String, String> hashMap,
                           Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .mainAdPopup(ConstantUtils.convertMapToBody(hashMap))
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

    /**
     * 评价弹窗
     *
     * @param transformer
     * @param context
     * @param callBack
     */
    @Override
    public void getCommentPopup(Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .userCommentNeeds()
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

    /**
     * 未读消息
     *
     * @param transformer
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
}
