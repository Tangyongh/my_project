package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingdingyijian.ddyj.base.BaseActivity;
import com.dingdingyijian.ddyj.databinding.ActivityWexinBindBinding;
import com.dingdingyijian.ddyj.event.RefreshEvent;
import com.dingdingyijian.ddyj.jpush.ExampleUtil;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.contract.WeiChatBindContract;
import com.dingdingyijian.ddyj.mvp.data.LoginInfo;
import com.dingdingyijian.ddyj.mvp.presenter.WeiChatBindPresenter;
import com.dingdingyijian.ddyj.utils.Constant;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.CountDownTimerUtils;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.noober.background.view.BLButton;
import com.noober.background.view.BLTextView;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/25
 * @describe: 微信登录绑定手机号
 */
@Route(path = Constant.PATH_WX_BIND)
public class WeiChatBindActivity extends BaseActivity<WeiChatBindContract.View, WeiChatBindContract.Presenter, ActivityWexinBindBinding> implements WeiChatBindContract.View {

    //微信用户信息
    @Autowired()
    String unionId;
    @Autowired()
    String name;
    @Autowired()
    String openId;
    private CountDownTimerUtils mDownTimerUtils;

    @Override
    public WeiChatBindContract.Presenter createPresenter() {
        return new WeiChatBindPresenter(mContext);
    }

    @Override
    public WeiChatBindContract.View createView() {
        return this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EditText etPhone = getBinding().etPhone;
        EditText etCode = getBinding().etCode;
        BLTextView sendCodeTV = getBinding().sendCodeTV;
        BLButton btnWxBindLogin = getBinding().btnWxBindLogin;
        //发送验证码
        getPresenter().clickSendCode(sendCodeTV, etPhone);
        //绑定并登录
        getPresenter().clickWeiXinBind(btnWxBindLogin, etPhone, etCode, unionId, name, openId);
    }

    //登录成功
    @Override
    public void getLoginResult(LoginBean loginBean) {
        //缓存登录信息
        LoginInfo.setUserInfo(loginBean);
        setAlias(loginBean);
        //登录成功的时候发送消息，给主界面刷新下界面
        LiveEventBus.get(RefreshEvent.class).post(new RefreshEvent());
        finish();
    }

    //微信绑定手机号之后调用登录接口
    @Override
    public void getWeiXinBindResult() {
        LoginBean loginBean = LoginInfo.getLoginBean(unionId, "", "4");
        getPresenter().getLogin(loginBean);
    }

    //获取登录验证码
    @Override
    public void getSendCodeResult() {
        ToastUtil.showMsg(mContext, "短信验证码已发送，请注意查收");
        mDownTimerUtils = new CountDownTimerUtils(getBinding().sendCodeTV, 60000, 1000);
        mDownTimerUtils.start();
    }

    //设置别名
    private void setAlias(LoginBean loginBean) {
        String alias = ExampleUtil.getAlias(mContext, loginBean);
        //设置极光推送别名
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("regIdAlias", alias);
        hashMap.put("registrationId", PreferenceUtil.getInstance().getString(ConstantOther.APP_PUSH_ID));
        getPresenter().getAlias(hashMap);
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
