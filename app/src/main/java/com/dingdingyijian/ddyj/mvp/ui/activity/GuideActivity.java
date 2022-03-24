package com.dingdingyijian.ddyj.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.adapter.GuideRecyclerAdapter;
import com.dingdingyijian.ddyj.databinding.ActivityGuideBinding;
import com.dingdingyijian.ddyj.utils.Constant;
import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tyh on 2019/6/3
 * 引导页
 */
@Route(path = Constant.PATH_GUIDE)
public class GuideActivity extends RxAppCompatActivity {


    private GuideRecyclerAdapter mAdapter;
    private ActivityGuideBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //状态栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .autoStatusBarDarkModeEnable(true)
                .autoNavigationBarDarkModeEnable(true)
                .navigationBarColor(R.color.text_color_white)
                .init();
        initView();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        //去除边缘阴影
        View child = mBinding.vpGuidePager.getChildAt(0);
        if (child instanceof RecyclerView) {
            child.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide_01);
        list.add(R.mipmap.guide_02);
        list.add(R.mipmap.guide_03);
        mAdapter = new GuideRecyclerAdapter(list);
        mBinding.vpGuidePager.setAdapter(mAdapter);
        mBinding.vpGuidePager.registerOnPageChangeCallback(mCallback);
        mBinding.cvGuideIndicator.setViewPager(mBinding.vpGuidePager);

        mBinding.btnGuideComplete.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.PATH_MAIN).navigation();
            finish();
        });
    }

    private ViewPager2.OnPageChangeCallback mCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mBinding.vpGuidePager.getCurrentItem() == mAdapter.getItemCount() - 1 && positionOffsetPixels > 0) {
                mBinding.cvGuideIndicator.setVisibility(View.VISIBLE);
                mBinding.btnGuideComplete.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                boolean last = mBinding.vpGuidePager.getCurrentItem() == mAdapter.getItemCount() - 1;
                mBinding.cvGuideIndicator.setVisibility(last ? View.INVISIBLE : View.VISIBLE);
                mBinding.btnGuideComplete.setVisibility(last ? View.VISIBLE : View.INVISIBLE);
            }
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.vpGuidePager.unregisterOnPageChangeCallback(mCallback);
    }

    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
