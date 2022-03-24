package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.contract.RegisteredContract;
import com.dingdingyijian.ddyj.mvp.model.RegisteredModel;
import com.dingdingyijian.ddyj.net.callback.RxHelper;
import com.dingdingyijian.ddyj.userinfo.RegisteredInfo;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/19 18:18
 * @description:
 */
public class RegisteredPresenter extends RegisteredContract.Presenter {

    private Context mContext;
    private RegisteredModel mModel;


    public RegisteredPresenter(Context context) {
        this.mContext = context;
        this.mModel = new RegisteredModel();
    }

    /**
     * 注册
     *
     * @param hashMap
     */
    @Override
    public void getRegistered(HashMap<String, String> hashMap) {
        mModel.getRegistered(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object o) {
                getView().getRegisteredResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(errorMsg);
            }
        });
    }

    /**
     * 获取验证
     *
     * @param mobile
     * @param type
     */
    @Override
    public void getSendCode(String mobile, String type) {
        mModel.getSendCode(mobile, type, mContext, new BaseModelCallBack<Object>() {
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
     * 点击发送验证码
     *
     * @param textView
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
                getSendCode(phone.getText().toString().trim(), "userRegister");
            }
        });
    }

    /**
     * 点击注册
     *
     * @param btnRegister
     * @param phone
     * @param code
     * @param pwd
     * @param confirmPwd
     * @param checkBox
     */
    @Override
    public void clickRegistered(BLButton btnRegister, EditText phone, EditText code, EditText pwd, EditText confirmPwd, CheckBox checkBox) {
        btnRegister.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg("手机号码错误，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString().trim())) {
                    ToastUtil.showMsg("短信验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
                    ToastUtil.showMsg("密码不能为空");
                    return;
                }
                if (pwd.getText().toString().trim().length() < 6) {
                    ToastUtil.showMsg("请输入6~20位的密码");
                    return;
                }
                if (!pwd.getText().toString().equals(confirmPwd.getText().toString())) {
                    ToastUtil.showMsg("密码不一致");
                    return;
                }
                if (!checkBox.isChecked()) {
                    ToastUtil.showMsg("请勾选并阅读《用户使用协议》、《隐私政策》");
                    return;
                }
                String mobile = phone.getText().toString().trim();
                String password = pwd.getText().toString().trim();
                String smsCode = code.getText().toString().trim();
                HashMap<String, String> params = RegisteredInfo.getParams(mobile, password, smsCode);
                //调用注册接口
                getRegistered(params);
            }
        });
    }
}
