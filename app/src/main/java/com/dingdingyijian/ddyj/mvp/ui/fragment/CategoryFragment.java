package com.dingdingyijian.ddyj.mvp.ui.fragment;

import android.os.Bundle;

import com.dingdingyijian.ddyj.base.BaseFragment;
import com.dingdingyijian.ddyj.base.BaseLazyFragment;
import com.dingdingyijian.ddyj.databinding.FragmentCategoryBinding;
import com.dingdingyijian.ddyj.mvp.contract.CategoryFragmentContract;
import com.dingdingyijian.ddyj.mvp.presenter.CategoryFragmentPresenter;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.mvp.ui.fragment
 */
public class CategoryFragment extends BaseFragment<CategoryFragmentContract.View, CategoryFragmentContract.Presenter, FragmentCategoryBinding> implements CategoryFragmentContract.View  {


    public static CategoryFragment getInstance() {
        return new CategoryFragment();
    }


    @Override
    public CategoryFragmentContract.Presenter createPresenter() {
        return new CategoryFragmentPresenter(mContext);
    }

    @Override
    public CategoryFragmentContract.View createView() {
        return this;
    }

    @Override
    public void initView(Bundle bundle) {

    }
}
