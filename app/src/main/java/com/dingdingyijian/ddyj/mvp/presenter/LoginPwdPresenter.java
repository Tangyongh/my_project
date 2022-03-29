package com.dingdingyijian.ddyj.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.LoginPwdContract;
import com.dingdingyijian.ddyj.mvp.model.LoginPwdModel;
import com.dingdingyijian.ddyj.mvp.data.LoginInfo;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLButton;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DDYiJian
 * @time: 2022/3/9
 * @describe: 登录
 */
@SuppressWarnings("all")
public class LoginPwdPresenter extends LoginPwdContract.Presenter {

    private Context mContext;
    private LoginPwdModel loginPwdModel;

    public LoginPwdPresenter(Context context) {
        this.mContext = context;
        this.loginPwdModel = new LoginPwdModel();
    }

    /**
     * 密码登录
     *
     * @param loginBean
     */
    @Override
    public void getLogin(LoginBean loginBean) {
        loginPwdModel.getLogin(loginBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                //登录成功
                getView().getLoginResult((LoginBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
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
        loginPwdModel.getAlias(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });

    }

    /**
     * 点击显示隐藏密码
     *
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
     * 点击登录
     *
     * @param btnPwdLogin
     * @param phone
     * @param pwd
     * @param checkBox
     */
    @Override
    public void clickLogin(BLButton btnPwdLogin, EditText phone, EditText pwd, CheckBox checkBox) {
        btnPwdLogin.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                //判断是否为空
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码或登录名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "密码不能为空");
                    return;
                }
                if (pwd.getText().toString().length() < 6) {
                    ToastUtil.showMsg(mContext, "密码长度错误");
                    return;
                }

                if (!checkBox.isChecked()) {
                    ToastUtil.showMsg(mContext, "请勾选并阅读《用户使用协议》、《隐私政策》");
                    return;
                }
                LoginBean loginBean = LoginInfo.getLoginBean(phone.getText().toString().trim(), pwd.getText().toString().trim(), "1");
                //登录接口
                getLogin(loginBean);

            }
        });

    }

    /**
     * 点击微信登录
     *
     * @param checkBox
     */
    @Override
    public void clickWxLogin( ImageView imageView, CheckBox checkBox) {
        imageView.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                if (!checkBox.isChecked()) {
                    ToastUtil.showMsg(mContext, "请勾选并阅读《用户使用协议》、《隐私政策》");
                    return;
                }
                showDialog();
                UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        cancelShowDialog();
                        if (map != null && !map.isEmpty()) {
                            String mOpenId = map.get("openid");
                            String mUnionId = map.get("unionid");
                            String mName = map.get("name");
                            LoginBean loginBean = LoginInfo.getLoginBean(mUnionId, "", "4");
                            //登录接口
                            getLogin(loginBean);
                            //把微信登录的信息回传
                            getView().getWxLoginResult(mOpenId, mUnionId, mName);
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        cancelShowDialog();
                        ToastUtil.showMsg(mContext, "登录失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        ToastUtil.showMsg(mContext, "取消登录");
                        cancelShowDialog();

                    }
                });
            }
        });
    }
}


