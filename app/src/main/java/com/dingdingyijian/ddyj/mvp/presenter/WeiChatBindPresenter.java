package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.WeiChatBindContract;
import com.dingdingyijian.ddyj.mvp.data.WeXinBindResult;
import com.dingdingyijian.ddyj.mvp.model.WeiChatBindModel;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantUtils;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/25
 * @describe: com.dingdingyijian.ddyj.mvp.presenter
 */
public class WeiChatBindPresenter extends WeiChatBindContract.Presenter {

    private Context mContext;
    private WeiChatBindModel mModel;

    public WeiChatBindPresenter(Context context) {
        this.mContext = context;
        this.mModel = new WeiChatBindModel();
    }


    //绑定
    @Override
    public void getWeiXinBind(HashMap<String, String> hashMap) {
        mModel.getWeiXinBind(hashMap, mContext, new BaseModelCallBack() {
            @Override
            public void onNext(Object o) {
                getView().getWeiXinBindResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //登录
    @Override
    public void getLogin(LoginBean loginBean) {
        mModel.getLogin(loginBean, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getLoginResult((LoginBean) result);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });
    }

    //设置推送别名
    @Override
    public void getAlias(HashMap<String, String> hashMap) {
        mModel.getAlias(hashMap, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
            }

            @Override
            public void onError(String errorMsg) {
            }
        });

    }

    //获取验证码
    @Override
    public void getSendCode(String mobile, String type) {
        mModel.getSendCode(mobile, type, mContext, new BaseModelCallBack<Object>() {
            @Override
            public void onNext(Object result) {
                getView().getSendCodeResult();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtil.showMsg(mContext, errorMsg);
            }
        });

    }

    //点击绑定手机号并登录
    @Override
    public void clickWeiXinBind(BLButton btnPwdLogin, EditText phone, EditText code, String unionId, String name, String openId) {
        btnPwdLogin.setOnClickListener(v -> {
            if (!ComUtil.isFastClick()) {
                if (TextUtils.isEmpty(phone.toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码不能为空");
                    return;
                }
                if (!ConstantUtils.isMobile(phone.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "手机号码错误，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString().trim())) {
                    ToastUtil.showMsg(mContext, "验证码不能为空");
                    return;
                }
                String mobile = phone.getText().toString().trim();
                String smsCode = code.getText().toString().trim();
                HashMap<String, String> params = WeXinBindResult.getParams(mobile, smsCode, openId, unionId, name);
                //微信登录绑定手机号
                getWeiXinBind(params);
            }
        });
    }

    //点击获取验证码
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
                getSendCode(phone.getText().toString().trim(), "smsLogin");
            }
        });
    }
}
