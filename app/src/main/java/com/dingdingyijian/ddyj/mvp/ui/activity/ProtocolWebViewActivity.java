package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityProtocolBinding;
import com.dingdingyijian.ddyj.mvp.bean.HtmlContentBean;
import com.dingdingyijian.ddyj.mvp.contract.ProtocolWebViewContract;
import com.dingdingyijian.ddyj.mvp.presenter.ProtocolWebViewPresenter;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.Constant;

/**
 * @author: tyh
 * @date: 2022/3/20 17:37
 * @description: 展示富文本协议内容
 */
@Route(path = Constant.PATH_WEB_CONTENT)
public class ProtocolWebViewActivity extends BaseActivity<ProtocolWebViewContract.View, ProtocolWebViewContract.Presenter, ActivityProtocolBinding>
        implements ProtocolWebViewContract.View {

    @Autowired()
    String code;

    @Override
    public ProtocolWebViewContract.Presenter createPresenter() {
        return new ProtocolWebViewPresenter(mContext);
    }

    @Override
    public ProtocolWebViewContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //配置WebView
        getPresenter().initWebView(getBinding().webView);
        //请求富文本
        getPresenter().getHtmlContent(code);
        //关闭页面
        getBinding().toolbar.contentBack.setOnClickListener(v -> finish());
    }

    //富文本内容
    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public void getWebViewResult(HtmlContentBean htmlContentBean) {
        getBinding().toolbar.tvTitleCenterName.setText(htmlContentBean.getTitle());
        StringBuilder sb = new StringBuilder();
        sb.append(ComUtil.getHtmlData(htmlContentBean.getContent()));
        getBinding().webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
    }

    @Override
    protected void onDestroy() {
        getPresenter().destroyWebView(getBinding().webView);
        super.onDestroy();
    }
}
