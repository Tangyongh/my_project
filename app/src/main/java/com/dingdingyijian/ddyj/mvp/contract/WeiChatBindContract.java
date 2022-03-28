package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.widget.EditText;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/25
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface WeiChatBindContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {
        //登录请求结果
        void getLoginResult(LoginBean loginBean);

        //微信登录绑定手机号
        void getWeiXinBindResult();

        //发送验证码成功
        void getSendCodeResult();
    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {
        //微信登录绑定手机号
        public abstract void getWeiXinBind(HashMap<String, String> hashMap);

        //登录请求
        public abstract void getLogin(LoginBean loginBean);

        //设置极光推送别名
        public abstract void getAlias(HashMap<String, String> hashMap);

        //发送登录验证码
        public abstract void getSendCode(String mobile, String type);

        //点击登录
        public abstract void clickWeiXinBind(BLButton btnPwdLogin, EditText phone, EditText code, String unionId, String name, String openId);

        //点击发送验证码
        public abstract void clickSendCode(BLTextView textView, EditText phone);
    }

    @SuppressWarnings("rawtypes")
    interface Model {
        //微信登录绑定手机号
        void getWeiXinBind(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack);

        //登录
        void getLogin(LoginBean loginBean, Context context, BaseModelCallBack callBack);

        //设置极光推送别名
        void getAlias(HashMap<String, String> hashMap, Context context, BaseModelCallBack callBack);

        //获取登录验证码
        void getSendCode(String mobile, String type, Context context, BaseModelCallBack callBack);

    }

}
