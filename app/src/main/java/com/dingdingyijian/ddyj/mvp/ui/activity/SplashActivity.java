package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

@Route(path = Constant.PATH_SPLASH)
public class SplashActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止重复创建的问题，第一次安装完成启动，和home键退出点击launcher icon启动会重复
        if (!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        if (ConstantUtils.getVersionCode()) {
            //跳转到引导页
            toGuideActivity();
        } else {
            //跳转主页
            toNextActivity();
        }
    }

    /**
     * `
     * 跳转到引导页
     */
    private void toGuideActivity() {
        ARouter.getInstance().build(Constant.PATH_GUIDE).greenChannel().navigation();
        finish();
    }

    /**
     * 跳转到主页
     */
    private void toNextActivity() {
        //greenChannel  表示所有的拦截器失效
        ARouter.getInstance().build(Constant.PATH_MAIN).greenChannel().navigation();
        finish();
    }


    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
