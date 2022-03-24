package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;

/**
 * @author: tyh
 * @date: 2022/3/19 18:17
 * @description: 忘记密码
 */
public interface ForgetPwdContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {
        //设置新密码
        void getSetPasswordResult();

        //发送验证码成功
        void getSendCodeResult();
    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {
        //注册
        public abstract void getSetPassword(HashMap<String, String> hashMap);

        //发送登录验证码
        public abstract void getSendCode(String mobile, String type);

        //点击显示隐藏密码
        public abstract void clickVisibilityOrGone(EditText editText, ImageButton imageButton);

        //点击发送验证码
        public abstract void clickSendCode(BLTextView textView, EditText phone);

        //点击注册
        public abstract void clickSetPassword(BLButton btnRegister, EditText phone, EditText code, EditText pwd, EditText confirmPwd);

    }

    @SuppressWarnings("rawtypes")
    interface Model {
        //设置新密码
        void getSetPassword(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack);

        //获取验证码
        void getSendCode(String mobile, String type, Context context, BaseModelCallBack callBack);
    }

}
