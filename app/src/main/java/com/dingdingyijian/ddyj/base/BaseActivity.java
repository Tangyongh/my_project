package com.dingdingyijian.ddyj.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.KeyboardAction;
import com.dingdingyijian.ddyj.view.LoadingProgressDialog;
import com.dylanc.viewbinding.ViewBindingUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import me.jessyan.autosize.AutoSizeCompat;

/**
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 */
public abstract class BaseActivity<V extends BaseViewImp, P extends BasePresenter<V>, VB extends ViewBinding> extends RxAppCompatActivity implements KeyboardAction {

    //引用V层和P层
    private P presenter;
    private V view;
    private VB binding;
    protected Context mContext;
    private LoadingProgressDialog mLoadingDialog;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initConfig(savedInstanceState);
    }

    private void initConfig(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止所有的activity横屏
        binding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        setContentView(binding.getRoot());
        ARouter.getInstance().inject(this);
        //状态栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .autoStatusBarDarkModeEnable(true)
                .autoNavigationBarDarkModeEnable(true)
                .navigationBarColor(R.color.text_color_white)
                .init();
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }
        initSoftKeyboard();
        initLoadingDialog();
        initView(savedInstanceState);
        //当APP长时间运行在后台，用户从后台返回时，判断pid是否一样，不一致程序代表被杀死，直接返回启动页面
        if (savedInstanceState != null) {
            int currentPID = android.os.Process.myPid(); //获取当前pid
            String savePid = savedInstanceState.getString(ConstantOther.PID); //内存不足时保存的pid
            if (!String.valueOf(currentPID).equals(savePid)) {
                ARouter.getInstance().build(Constant.PATH_SPLASH).navigation();
            }
        }
    }


    //由子类指定具体类型
    public abstract P createPresenter();
    public abstract V createView();
    protected abstract void initView(Bundle savedInstanceState);
    public VB getBinding() {
        return binding;
    }



    public void initLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingProgressDialog();
            mLoadingDialog.createLoadingDialog(this);
        }
    }

    /**
     * 加载进度
     */
    public void showCustomProgressDialog() {
        if (!ComUtil.isDestroy(this)) {
            mLoadingDialog.showDialog();
        }
    }

    /**
     * 取消加载进度
     */
    public void cancelCustomProgressDialog() {
        if (!ComUtil.isDestroy(this)) {
            mLoadingDialog.closeDialog();
        }
    }
    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ConstantOther.PID, String.valueOf(android.os.Process.myPid()));
    }
}
