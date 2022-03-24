package com.dingdingyijian.ddyj.mvp.contract;

import com.dingdingyijian.ddyj.base.BasePresenter;
import com.dingdingyijian.ddyj.base.BaseViewImp;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.contract
 */
public interface CategoryFragmentContract {

    /**
     * View
     */
    interface View extends BaseViewImp {
    }

    /**
     * Presenter
     */
    abstract class Presenter extends BasePresenter<View> {

    }


}
