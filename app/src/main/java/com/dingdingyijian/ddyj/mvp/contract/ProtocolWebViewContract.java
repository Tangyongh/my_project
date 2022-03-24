package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.webkit.WebView;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.HtmlContentBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

/**
 * @author: tyh
 * @date: 2022/3/20 22:27
 * @description: 富文本
 */
public interface ProtocolWebViewContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {
        //请求成功
        void getWebViewResult(HtmlContentBean htmlContentBean);

    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {
        //请求富文本内容
        public abstract void getHtmlContent(String code);
        //初始化配置
        public abstract void initWebView(WebView webView);
        //销毁WebView
        public abstract void destroyWebView(WebView webView);
    }

    interface Model {
        //请求富文本内容
        void getHtmlContent( String code, Context context, BaseModelCallBack callBack);
    }

}
