package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityLoginpwdBinding;
import com.dingdingyijian.ddyj.event.RefreshEvent;
import com.dingdingyijian.ddyj.jpush.ExampleUtil;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginPwdContract;
import com.dingdingyijian.ddyj.mvp.presenter.LoginPwdPresenter;
import com.dingdingyijian.ddyj.mvp.data.LoginInfo;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.noober.background.view.BLButton;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;


/**
 * 密码登录页面
 */
@Route(path = Constant.PATH_LOGIN_PWD)
public class LoginPwdActivity extends BaseActivity<LoginPwdContract.View, LoginPwdContract.Presenter, ActivityLoginpwdBinding> implements LoginPwdContract.View {


    /**
     * 微信登录用户信息
     */
    private String mUnionId;
    private String mName;
    private String mOpenId;

    @Override
    public LoginPwdContract.Presenter createPresenter() {
        return new LoginPwdPresenter(mContext);
    }

    @Override
    public LoginPwdContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickListener();
        onEvent();
    }


    //点击事件
    private void onClickListener() {
        BLButton btnPwdLogin = getBinding().btnPwdLogin;
        EditText etPhone = getBinding().etPhone;
        EditText etPwd = getBinding().etPwd;
        CheckBox check = getBinding().check;
        ImageView wxLogin = getBinding().wxLogin;
        //点击登录
        getPresenter().clickLogin(btnPwdLogin, etPhone, etPwd, check);
        //点击微信登录
        getPresenter().clickWxLogin(this, wxLogin, check);

        //跳转验证码登录
        getBinding().tvCodeLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.PATH_LOGIN_CODE).navigation();
            finish();
        });
        //注册
        getBinding().contentRegister.setOnClickListener(v -> ARouter.getInstance().build(Constant.PATH_REGISTER).navigation());
        //点击显示密码或隐藏
        getBinding().btnGone.setOnClickListener(v -> getPresenter().clickVisibilityOrGone(getBinding().etPwd, getBinding().btnGone));
        //点击关闭页面
        getBinding().toolbar.contentBack.setOnClickListener(v -> finish());
        //忘记密码
        getBinding().tvForgetPassword.setOnClickListener(v -> ARouter.getInstance().build(Constant.PATH_FORGET_PWD).navigation());
        //点击跳转”用户使用协议“
        getBinding().tvUserProtocol.setOnClickListener(v -> {
            //useAgreement：用户使用协议
            ARouter.getInstance().build(Constant.PATH_WEB_CONTENT).withString("code", "useAgreement").navigation();
        });
        //用户隐私协议
        getBinding().tvProtocol.setOnClickListener(v -> {
            //secretExplain：用户使用协议
            ARouter.getInstance().build(Constant.PATH_WEB_CONTENT).withString("code", "secretExplain").navigation();
        });
    }


    //登录成功
    @Override
    public void getLoginResult(LoginBean loginBean) {
        //缓存登录信息
        LoginInfo.setUserInfo(loginBean);
        setAlias(loginBean);
        //登录成功的时候发送消息，给主界面刷新下界面
        LiveEventBus.get(RefreshEvent.class).post(new RefreshEvent());
        finish();
    }


    //微信登录的用户信息
    @Override
    public void getWxLoginResult(String openid, String unionid, String name) {
        mOpenId = openid;
        mUnionId = unionid;
        mName = name;
    }


    //设置别名
    private void setAlias(LoginBean loginBean) {
        String alias = ExampleUtil.getAlias(mContext, loginBean);
        //设置极光推送别名
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("regIdAlias", alias);
        hashMap.put("registrationId", PreferenceUtil.getInstance().getString(ConstantOther.APP_PUSH_ID));
        getPresenter().getAlias(hashMap);
    }

    //消息回调处理
    private void onEvent() {
        //微信登录的时候，没有绑定手机号
        LiveEventBus.get("WeiChat_Bind", String.class).observe(this, s -> {
            //跳转绑定页面
            ARouter.getInstance()
                    .build(Constant.PATH_WX_BIND)
                    .withString("unionId", mUnionId)
                    .withString("name", mName)
                    .withString("openId", mOpenId)
                    .navigation();
            finish();

        });

        //接收注冊成功后返回的手机号码
        LiveEventBus.get("register_success", String.class).observe(this, s -> getBinding().etPhone.setText(s));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
