package com.dingdingyijian.ddyj.base;


import android.content.Context;

import com.dingdingyijian.ddyj.view.LoadingProgressDialog;

/**
 * BasePresenter
 */
public abstract class BasePresenter<V extends BaseViewImp>{

    private V mView;
    //加载进度框
    private LoadingProgressDialog loadingProgressDialog = null;

    public V getView(){
        return mView;
    }

    public void attachView(V v){
        mView = v;
    }

    public void detachView(){
        mView = null;
    }




    public void showDialog(){
        if (mView != null){
            loadingProgressDialog = new LoadingProgressDialog();
            loadingProgressDialog.createLoadingDialog((Context) getView());
            loadingProgressDialog.showDialog();
        }
    }

    //关闭进度加载
    public void cancelShowDialog() {
        if (loadingProgressDialog != null) {
            loadingProgressDialog.closeDialog();
            loadingProgressDialog = null;
        }
    }
}
