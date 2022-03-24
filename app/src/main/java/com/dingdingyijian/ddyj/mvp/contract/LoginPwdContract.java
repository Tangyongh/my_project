package com.dingdingyijian.ddyj.mvp.contract;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dingdingyijian.ddyj.base.BaseModelCallBack;
import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.base.BaseViewImp;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.noober.background.view.BLButton;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.HashMap;


/**
 * @author: DDYiJian
 * @time: 2022/3/9
 * @describe: 登录model
 */
public interface LoginPwdContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {
        //登录请求结果
        void getLoginResult(LoginBean loginBean);
        //把微信登录的信息回传
        void getWxLoginResult(String openid,String unionid,String name);
    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {
        //登录请求
        public abstract void getLogin(LoginBean loginBean);
        //设置极光推送别名,
        public abstract void getAlias(HashMap<String,String> hashMap);
        //点击显示隐藏密码
        public abstract void  clickVisibilityOrGone(EditText editText, ImageButton imageButton);
        //点击登录
        public abstract void  clickLogin(BLButton btnPwdLogin, EditText phone, EditText pwd, CheckBox checkBox);
        //点击微信登录
        public abstract void clickWxLogin(Context context, ImageView wxLogin,CheckBox checkBox);
    }


    @SuppressWarnings("rawtypes")
    interface Model{

        //登录
        void getLogin( LoginBean loginBean, Context context, BaseModelCallBack callBack);
        //设置极光推送别名
        void getAlias( HashMap<String,String> hashMap, Context context, BaseModelCallBack callBack);
    }
}
