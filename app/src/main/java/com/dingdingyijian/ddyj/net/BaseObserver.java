package com.dingdingyijian.ddyj.net;


import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.net.helper.ApiException;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.dingdingyijian.ddyj.view.LoadingProgressDialog;
import com.jeremyliao.liveeventbus.LiveEventBus;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 数据返回统一处理
 *
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    //是否显示加载进度
    private boolean mShowDialog;
    //加载进度框
    private LoadingProgressDialog loadingProgressDialog = null;
    private Context mContext;
    private Disposable mDisposable;

    //请求成功
    public abstract void onSuccess(T result);

    //请求失败
    public abstract void onFailure(Throwable e, String errorMsg);


    public BaseObserver(Context context, boolean showDialog) {
        this.mContext = context;
        this.mShowDialog = showDialog;
    }

    public BaseObserver(Context context) {
        this(context, true);
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        //对基础数据 进行统一处理
        if (response.getCode() == Constant.successCode) {
            onSuccess(response.getData());
        } else {
            //请求失败处理
            getCode(response.getCode());
            onFailure(new Exception(response.getMessage()), response.getMessage());
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        if (loadingProgressDialog == null && mShowDialog) {
            loadingProgressDialog = new LoadingProgressDialog();
            loadingProgressDialog.createLoadingDialog(mContext);
            loadingProgressDialog.showDialog();
        }
        if (!ComUtil.isConnected(mContext)) {
            ToastUtil.showMsg("未连接网络");
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onCancelRequest();
        getCode(ApiException.getCode());
        onFailure(e, ApiException.getErrorMessage());
    }

    @Override
    public void onComplete() {
        onCancelRequest();
    }


    //关闭进度加载
    public void cancelShowDialog() {
        if (loadingProgressDialog != null && mShowDialog) {
            loadingProgressDialog.closeDialog();
            loadingProgressDialog = null;
        }
    }


    //取消订阅
    public void onCancelRequest() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        cancelShowDialog();
    }


    //接口请求失败返回的状态code
    private void getCode(int code) {
        Logger.d("Observer执行了==", "code===================" + code);
        switch (code) {
            case 401: //登录失效
            case 60026:
                ARouter.getInstance().build(Constant.PATH_LOGIN_PWD).navigation();
                break;
            case 60032: //微信登录未绑定手机号
                LiveEventBus.get("WeiChat_Bind", String.class).post("WeiChat_Bind");
                break;
        }
    }
}

