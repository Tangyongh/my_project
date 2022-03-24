package com.dingdingyijian.ddyj.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.contract.MainContract;
import com.dingdingyijian.ddyj.mvp.model.MainModel;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.permissionx.guolindev.PermissionX;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/9
 * @describe: com.dingdingyijian.ddyj.mvp.presenter
 */
public class MainPresenter extends MainContract.Presenter {

    private Context mContext;
    private MainModel mModel;

    public MainPresenter(Context context) {
        this.mContext = context;
        this.mModel = new MainModel();
    }


    //广告弹窗
    @Override
    public void getAdPopup(HashMap<String, String> hashMap) {
        mModel.getAdPopup(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getAdPopupResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }

    //评价弹窗
    @Override
    public void getCommentPopup() {
        mModel.getCommentPopup(mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getCommentPopupResult();
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
        mModel.getNoticeNoRead(mContext, new BaseModelCallBack<Object>() {
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

    //获取定位权限
    @Override
    public void getPermissions(Context context) {
        PermissionX.init((FragmentActivity) context)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
                    //请求获取权限回调
                    scope.showRequestReasonDialog(deniedList, "请求获取位置权限，用于地图定位接、发单位置", "我已明白");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    //点击禁止权限之后弹窗去设置打开权限
                    scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "去开启权限");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        ToastUtil.showMsg("您拒绝了如下权限" + deniedList);
                    }
                });
    }

    //状态栏
    @Override
    public void initStatusBar(Context context) {
        ImmersionBar.with((Activity) context)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.colorPrimary)
                .autoStatusBarDarkModeEnable(true)
                .navigationBarColor(R.color.text_color_white) //导航栏颜色，不写默认黑色
                .autoNavigationBarDarkModeEnable(true)
                .init();
    }
}
