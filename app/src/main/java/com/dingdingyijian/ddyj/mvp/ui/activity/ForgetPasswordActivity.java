package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityForgetPasswordBinding;
import com.dingdingyijian.ddyj.mvp.contract.ForgetPwdContract;
import com.dingdingyijian.ddyj.mvp.presenter.ForgetPwdPresenter;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.CountDownTimerUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

/**
 * @author: tyh
 * @date: 2022/3/20 16:23
 * @description: 忘记密码
 */
@Route(path = Constant.PATH_FORGET_PWD)
public class ForgetPasswordActivity extends BaseActivity<ForgetPwdContract.View, ForgetPwdContract.Presenter, ActivityForgetPasswordBinding> implements ForgetPwdContract.View {

    private CountDownTimerUtils mDownTimerUtils;

    @Override
    public ForgetPwdContract.Presenter createPresenter() {
        return new ForgetPwdPresenter(mContext);
    }

    @Override
    public ForgetPwdContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickListener();
    }

    //点击事件
    private void onClickListener() {
        BLButton btnSetPwd = getBinding().btnSetPwd;
        BLTextView sendCodeTV = getBinding().sendCodeTV;
        EditText etCode = getBinding().etCode;
        EditText etPhone = getBinding().etPhone;
        EditText etPwd = getBinding().etPwd;
        EditText etPwdConfirm = getBinding().etPwdConfirm;
        //点击获取验证码
        getPresenter().clickSendCode(sendCodeTV, etPhone);
        //点击修改新密码
        getPresenter().clickSetPassword(btnSetPwd, etPhone, etCode, etPwd, etPwdConfirm);
        //点击关闭页面
        getBinding().toolbar.contentBack.setOnClickListener(v -> finish());
        //拨打客服电话
        getBinding().contentCall.setOnClickListener(v -> ConstantUtils.jumCall(mContext));
        //点击密码是否明文显示
        getBinding().btnGone.setOnClickListener(v -> getPresenter().clickVisibilityOrGone(etPwd, getBinding().btnGone));
        getBinding().btnGoneConfirm.setOnClickListener(v -> getPresenter().clickVisibilityOrGone(etPwdConfirm, getBinding().btnGoneConfirm));
    }

    //修改新密码成功
    @Override
    public void getSetPasswordResult() {
        ToastUtil.showMsg("修改密码成功");
        LiveEventBus.get("register_success", String.class).post(getBinding().etPhone.getText().toString().trim());
        finish();
    }

    //获取验证码成功
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
