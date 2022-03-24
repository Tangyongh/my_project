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
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.dingdingyijian.ddyj.utils.LogUtils;
import com.dylanc.viewbinding.ViewBindingUtil;
import com.trello.rxlifecycle4.components.support.RxFragment;


/**
 * https://juejin.im/post/5adcb0e36fb9a07aa7673fbc
 * BaseLazyFragment 单fragment懒加载
 * <p>
 * * 生命周期执行的方法 如下：
 * 第一次生成页面-->可见
 * setUserVisibleHint: ----->false
 * setUserVisibleHint: ----->true
 * onCreateView: -----> onCreateView
 * onStart: -----> onStart
 * onFragmentFirst: 首次可见
 * onFragmentFirst: -----> 子fragment进行初始化操作
 * onResume: -----> onResume
 * <p>
 * 可见-->第一次隐藏：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * <p>
 * 未销毁且不可见-->重新可见：
 * onStart: -----> onStart
 * onFragmentVisble: 可见
 * onFragmentVisble: -----> 子fragment每次可见时的操作
 * onResume: -----> onResume
 * <p>
 * 可见-->销毁：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * onDestroyView: -----> onDestroyView
 * <p>
 * 我们可以更具以上生命周期来操作不同的业务逻辑，
 * 请务必运行此module demo，观看打印日志来自定义逻辑。
 */
public abstract class BaseLazyFragment<V extends BaseViewImp, P extends BasePresenter<V>, VB extends ViewBinding> extends RxFragment {
    //引用V层和P层
    private P presenter;
    private V view;
    public Context mContext;
    protected VB binding;

    private boolean isLoadDataComplete;
    private boolean isViewCreated;

    public P getPresenter() {
        return presenter;
    }

    public BaseLazyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && !isLoadDataComplete) {
            isLoadDataComplete = true;
            initData();
        }
    }

    //由子类指定具体类型
    public abstract P createPresenter();

    public abstract V createView();

    public abstract void initView(Bundle bundle);

    protected abstract void initData();

    @SuppressWarnings("deprecation")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("----->" + isVisibleToUser);
        if (isVisibleToUser && !isLoadDataComplete && isViewCreated) {
            isLoadDataComplete = true;
            initData();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
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
