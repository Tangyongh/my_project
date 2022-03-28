package com.dingdingyijian.ddyj.mvp.contract;

import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseViewImp;

import java.util.HashMap;

/**
 * @author: DDYiJian
 * @time: 2022/3/28
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface ModifyBusinessCardContract {

    /**
     * View
     */
    interface View extends BaseViewImp {
        //上传用户头像
        void getUploadAvatarResult();
    }

    /**
     * Presenter
     */
    abstract class Presenter extends BasePresenter<ModifyBusinessCardContract.View> {
        //注册
        public abstract void getUploadAvatar(HashMap<String, String> hashMap);
    }

    interface Model {


    }
}
