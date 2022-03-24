package com.dingdingyijian.ddyj.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author: DDYiJian
 * @time: 2022/3/11
 * @describe: com.dingdingyijian.ddyj.adapter
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private String[] mTitles = {"施工", "叮叮商城", "工到", "个人中心"};

    public ViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
