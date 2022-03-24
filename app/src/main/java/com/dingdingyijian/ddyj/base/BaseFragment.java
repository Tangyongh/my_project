package com.dingdingyijian.ddyj.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.dylanc.viewbinding.ViewBindingUtil;
import com.trello.rxlifecycle4.components.support.RxFragment;

/**
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 */
public abstract class BaseFragment<V extends BaseViewImp, P extends BasePresenter<V>,VB extends ViewBinding> extends RxFragment {

    //引用V层和P层
    private P presenter;
    private V view;
    public Context mContext;
    protected VB binding;

    public P getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (this.view == null) {
            this.view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(this.view);
        }
        initView(savedInstanceState);
        return binding.getRoot();
    }

    //由子类指定具体类型
    public abstract P createPresenter();
    public abstract V createView();
    public abstract void initView(Bundle savedInstanceState);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        if (binding != null){
            binding = null;
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.mContext = context;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public VB getBinding() {
        return binding;
    }
}