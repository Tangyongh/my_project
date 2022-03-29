package com.dingdingyijian.ddyj.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.HtmlContentBean;
import com.dingdingyijian.ddyj.mvp.contract.ProtocolWebViewContract;
import com.dingdingyijian.ddyj.mvp.model.ProtocolWebViewModel;
import com.dingdingyijian.ddyj.utils.ToastUtil;

/**
 * @author: tyh
 * @date: 2022/3/20 23:07
 * @description:
 */
public class ProtocolWebViewPresenter extends ProtocolWebViewContract.Presenter {

    private Context mContext;

    private ProtocolWebViewModel mModel;

    public ProtocolWebViewPresenter(Context context) {
        this.mContext = context;
        this.mModel = new ProtocolWebViewModel();
    }

    /**
     * 请求富文本内容
     * @param code
     */
    @Override
    public void getHtmlContent(String code) {
        mModel.getHtmlContent(code, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getWebViewResult((HtmlContentBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(false);
        //设置编码
        settings.setDefaultTextEncodingName("utf-8");
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {
            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cancelShowDialog();
                super.onPageFinished(view, url);
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            //处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    //销毁WebView
    @Override
    public void destroyWebView(WebView webView) {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;    这一行代码，需要先onDetachedFromWindow()，再
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
    }


}
