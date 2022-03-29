package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.contract.ForgetPwdContract;
import com.dingdingyijian.ddyj.mvp.model.ForgetPwdModel;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/20 16:33
 * @description:
 */
public class ForgetPwdPresenter extends ForgetPwdContract.Presenter {

    private Context mContext;
    private ForgetPwdModel mModel;

    public ForgetPwdPresenter(Context context) {
        this.mContext = context;
        this.mModel = new ForgetPwdModel();
    }


    /**
     * 设置新密码
     *
     * @param hashMap
     */
    @Override
    public void getSetPassword(HashMap<String, String> hashMap) {
        mModel.getSetPassword(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getSetPasswordResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    /**
     * 获取验证码
     * @param mobile
     * @param type
     */
    @Override
    public void getSendCode(String mobile, String type) {
        mModel.getSendCode( mobile, type, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getSendCodeResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    /**
     * 点击是否明文显示密码
     * @param editText
     * @param imageButton
     */
    @Override
    public void clickVisibilityOrGone(EditText editText, ImageButton imageButton) {
        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setText(editText.getText());
            editText.setSelection(editText.getText().length());
            imageButton.setImageResource(R.mipmap.icon_pwd_visiblity);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageButton.setImageResource(R.mipmap.icon_pwd_gone);
            editText.setText(editText.getText());
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 点击获取验证码
     * @param textView
     * @param phone
     */
    @Override
    public void clickSendCode(BLTextView textView, EditText phone) {
        textView.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {

                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码错误，请重新输入");
                    return;
                }
                getSendCode(phone.getText().toString().trim(), "forgetPassword");
            }
        });
    }

    /**
     * 点击设置新密码
     * @param btnRegister
     * @param phone
     * @param code
     * @param pwd
     * @param confirmPwd
     */
    @Override
    public void clickSetPassword(BLButton btnRegister, EditText phone, EditText code, EditText pwd, EditText confirmPwd) {
        btnRegister.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()){
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码错误，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "短信验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "密码不能为空");
                    return;
                }
                if (pwd.getText().toString().trim().length() < 6) {
                    ToastUtil.showMsg(mContext, "请输入6~20位的密码");
                    return;
                }
                if (!pwd.getText().toString().trim().equals(confirmPwd.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "两次输入的密码不一致");
                    return;
                }
                String mobile = phone.getText().toString().trim();
                String password = pwd.getText().toString().trim();
                String smsCode = code.getText().toString().trim();
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("mobile",mobile);
                hashMap.put("password",password);
                hashMap.put("smsCode",smsCode);
                hashMap.put("imei","");
                //调用修改密码接口
                getSetPassword(hashMap);
            }
        });
    }
}
