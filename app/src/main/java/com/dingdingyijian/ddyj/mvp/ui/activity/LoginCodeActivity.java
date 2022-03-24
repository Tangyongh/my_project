package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityCodeLoginBinding;
import com.dingdingyijian.ddyj.event.LoginEvent;
import com.dingdingyijian.ddyj.jpush.ExampleUtil;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginCodeContract;
import com.dingdingyijian.ddyj.mvp.presenter.LoginCodePresenter;
import com.dingdingyijian.ddyj.mvp.data.LoginInfo;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.CountDownTimerUtils;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.noober.background.view.BLButton;

import java.util.HashMap;

/**
 * 验证码登录
 */
@Route(path = Constant.PATH_LOGIN_CODE)
public class LoginCodeActivity extends BaseActivity<LoginCodeContract.View, LoginCodeContract.Presenter, ActivityCodeLoginBinding> implements LoginCodeContract.View {

    private CountDownTimerUtils mDownTimerUtils;

    @Override
    public LoginCodeContract.Presenter createPresenter() {
        return new LoginCodePresenter(mContext);
    }

    @Override
    public LoginCodeContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickListener();
    }

    //点击事件
    private void onClickListener() {
        BLButton btnCodeLogin = getBinding().btnCodeLogin;
        EditText etPhone = getBinding().etPhone;
        EditText etCode = getBinding().etCode;
        CheckBox check = getBinding().check;
        //点击登录
        getPresenter().clickLogin(btnCodeLogin, etPhone, etCode, check);
        //点击获取验证码
        getPresenter().clickSendCode(getBinding().sendCodeTV, etPhone);

        //点击忘记密码
        getBinding().tvForgetPassword.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.PATH_FORGET_PWD).navigation();
            finish();
        });

        //点击跳转注册页面
        getBinding().contentRegister.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.PATH_REGISTER).navigation();
            finish();
        });

        //点击跳转密码登录页面
        getBinding().tvPwdLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.PATH_LOGIN_PWD).navigation();
            finish();
        });

        //点击标题返回按钮关闭页面
        getBinding().toolbar.contentBack.setOnClickListener(v -> finish());
    }


    //登录成功
    @Override
    public void getLoginResult(LoginBean loginBean) {
        //缓存登录信息
        LoginInfo.setUserInfo(loginBean);
        //登录成功的时候发送消息，给主界面刷新下界面
        LiveEventBus.get(LoginEvent.class).post(new LoginEvent());
        //设置推送别名
        setAlias(loginBean);
        finish();
    }

    //发送验证码成功
    @Override
    public void getSendCodeResult() {
        ToastUtil.showMsg("短信验证码已发送，请注意查收");
        mDownTimerUtils = new CountDownTimerUtils(getBinding().sendCodeTV, 60000, 1000);
        mDownTimerUtils.start();
    }

    //设置推送别名
    private void setAlias(LoginBean loginBean) {
        String alias = ExampleUtil.getAlias(mContext, loginBean);
        //设置极光推送别名
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("regIdAlias", alias);
        hashMap.put("registrationId", PreferenceUtil.getInstance().getString(ConstantOther.APP_PUSH_ID));
        getPresenter().getAlias(hashMap);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownTimerUtils != null) {
            mDownTimerUtils.cancel();
            mDownTimerUtils = null;
        }
    }
}
