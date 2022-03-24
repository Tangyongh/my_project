package com.dingdingyijian.ddyj.mvp.model;

import android.content.Context;

import com.dingdingyijian.ddyj.api.RetrofitUtil;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.HtmlContentBean;
import com.dingdingyijian.ddyj.mvp.contract.ProtocolWebViewContract;
import com.dingdingyijian.ddyj.net.callback.BaseObserver;
import com.dingdingyijian.ddyj.net.callback.RxHelper;

/**
 * @author: tyh
 * @date: 2022/3/20 22:28
 * @description:
 */
@SuppressWarnings("all")
public class ProtocolWebViewModel implements ProtocolWebViewContract.Model {

    /**
     * 请求富文本内容
     *
     * @param transformer
     * @param code
     * @param context
     * @param callBack
     */
    @Override
    public void getHtmlContent(String code, Context context, BaseModelCallBack callBack) {
        RetrofitUtil.getInstance().getApiService()
                .webHtmlContent(code)
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(new BaseObserver<HtmlContentBean>(context) {
                    @Override
                    public void onSuccess(HtmlContentBean result) {
                        callBack.onNext(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        callBack.onNext(errorMsg);
                    }
                });

    }
}
