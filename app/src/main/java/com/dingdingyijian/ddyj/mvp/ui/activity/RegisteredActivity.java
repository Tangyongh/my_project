package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityRegisterBinding;
import com.dingdingyijian.ddyj.mvp.contract.RegisteredContract;
import com.dingdingyijian.ddyj.mvp.presenter.RegisteredPresenter;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.CountDownTimerUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

/**
 * @author: tyh
 * @date: 2022/3/19 18:15
 * @description: 注册
 */
@Route(path = Constant.PATH_REGISTER)
public class RegisteredActivity extends BaseActivity<RegisteredContract.View, RegisteredContract.Presenter, ActivityRegisterBinding> implements RegisteredContract.View {

    //获取验证码倒计时
    private CountDownTimerUtils mDownTimerUtils;

    @Override
    public RegisteredContract.Presenter createPresenter() {
        return new RegisteredPresenter(mContext);
    }

    @Override
    public RegisteredContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickListener();

    }

    //其他点击事件
    private void onClickListener() {
        BLButton btnRegistered = getBinding().btnRegistered;
        EditText etPwd = getBinding().etPwd;
        EditText etPhone = getBinding().etPhone;
        BLTextView sendCodeTV = getBinding().sendCodeTV;
        EditText etPwdConfirm = getBinding().etPwdConfirm;
        EditText etCode = getBinding().etCode;
        CheckBox check = getBinding().check;
        //点击注册
        getPresenter().clickRegistered(btnRegistered,etPhone,etCode,etPwd,etPwdConfirm,check);
        //点击获取验证码
        getPresenter().clickSendCode(sendCodeTV,etPhone);

        //点击关闭页面
        getBinding().toolbar.contentBack.setOnClickListener(v -> finish());
        //拨打客服电话
        getBinding().contentCall.setOnClickListener(v -> ConstantUtils.jumCall(mContext));
        //是否显示明文密码
        getBinding().btnGone.setOnClickListener(v -> getPresenter().clickVisibilityOrGone(getBinding().etPwd,getBinding().btnGone));
        getBinding().btnGoneConfirm.setOnClickListener(v -> getPresenter().clickVisibilityOrGone(getBinding().etPwdConfirm,getBinding().btnGoneConfirm));
    }


    //注册成功
    @Override
    public void getRegisteredResult() {
        ToastUtil.showMsg("注册成功");
        //注册成功后把手机号码回显到登录界面
        LiveEventBus.get("register_success",String.class).post(getBinding().etPhone.getText().toString().trim());
        finish();
    }

    //获取注册验证码
    @Override
    public void getSendCodeResult() {
        ToastUtil.showMsg("短信验证码已发送，请注意查收");
        mDownTimerUtils = new CountDownTimerUtils(getBinding().sendCodeTV, 60000, 1000);
        mDownTimerUtils.start();
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
