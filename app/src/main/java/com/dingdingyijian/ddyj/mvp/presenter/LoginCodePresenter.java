package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginCodeContract;
import com.dingdingyijian.ddyj.mvp.model.LoginCodeModel;
import com.dingdingyijian.ddyj.mvp.data.LoginInfo;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/18 16:39
 * @description:
 */

public class LoginCodePresenter extends LoginCodeContract.Presenter {


    private Context mContext;
    private LoginCodeModel mLoginCodeModel;

    public LoginCodePresenter(Context context) {
        this.mContext = context;
        this.mLoginCodeModel = new LoginCodeModel();
    }


    /**
     * 登录
     *
     * @param loginBean
     */
    @Override
    public void getLogin(LoginBean loginBean) {
        mLoginCodeModel.getLogin( loginBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getLoginResult((LoginBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });

    }

    /**
     * 设置极光推送别名
     *
     * @param hashMap
     */
    @Override
    public void getAlias(HashMap<String, String> hashMap) {
        mLoginCodeModel.getAlias(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }

    /**
     * 发送验证码
     *
     * @param mobile
     * @param type
     */
    @Override
    public void getSendCode(String mobile, String type) {
        mLoginCodeModel.getSendCode(mobile, type, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getSendCodeResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });

    }

    /**
     * 点击登录
     *
     * @param btnPwdLogin
     * @param phone
     * @param code
     * @param checkBox
     */
    @Override
    public void clickLogin(BLButton btnPwdLogin, EditText phone, EditText code, CheckBox checkBox) {
        btnPwdLogin.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                //判断是否为空
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码错误，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString().trim())) {
                    ToastUtil.showMsg("验证码不能为空");
                    return;
                }

                if (!checkBox.isChecked()) {
                    ToastUtil.showMsg("请勾选并阅读《用户使用协议》、《隐私政策》");
                    return;
                }
                String userPhone = phone.getText().toString().trim();
                String smsCode = code.getText().toString().trim();
                LoginBean loginBean = LoginInfo.getLoginBean(userPhone, smsCode, "2");
                //登录接口
                getLogin(loginBean);
            }
        });

    }


    /**
     * 点击获取验证码
     *
     * @param phone
     */
    @Override
    public void clickSendCode(BLTextView textView, EditText phone) {
        textView.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {

                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码错误，请重新输入");
                    return;
                }
                getSendCode(phone.getText().toString().trim(), "smsLogin");
            }
        });
    }
}
