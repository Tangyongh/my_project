package com.dingdingyijian.ddyj.mvp.presenter;

import android.content.Context;

import com.dingdingyijian.ddyj.mvp.contract.CategoryFragmentContract;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.presenter
 */
public class CategoryFragmentPresenter extends CategoryFragmentContract.Presenter {

    private Context context;

    public CategoryFragmentPresenter(Context context) {
        this.context = context;
    }
}
