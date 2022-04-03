package com.dingdingyijian.ddyj.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.glide.GlideImage;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tyh on 19/11/20.
 * Name VerticalAutoScrollView
 * 跑马灯
 */
public class VerticalAutoScrollView extends LinearLayout {
    private Scroller mScroller;  //滚动实例
    private List<NeedsAcceptListBean> needsAcceptListBeans = new ArrayList<>();  //存放数据集合
    private final int DURING_TIME = 3000;  //滚动延迟
    private Context mContext;
    private float HEIGHT = 35;//滚动高度


    public VerticalAutoScrollView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public VerticalAutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    /**
     * 设置数据
     *
     * @param needsAcceptListBeans
     */
    @SuppressWarnings("JavaDoc")
    public void setData(List<NeedsAcceptListBean> needsAcceptListBeans) {
        this.needsAcceptListBeans = needsAcceptListBeans;
        if (needsAcceptListBeans != null) {
            if (needsAcceptListBeans.size() > 0) {
                removeAllViews();
                int size = needsAcceptListBeans.size();
                for (int i = 0; i < size; i++) {
                    addContentView(i);
                }
                if (needsAcceptListBeans.size() > 1) {
                    getLayoutParams().height = ComUtil.dip2px(mContext, (int) HEIGHT);  //调节滚动数据的高度
                    // 滚动
                    cancelAuto();
                    mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
                    smoothScrollBy(0, ComUtil.dip2px(mContext, (int) HEIGHT));
                }
            }

        }

    }


    /**
     * 重置数据
     */
    private void resetView() {
        NeedsAcceptListBean article = needsAcceptListBeans.get(0);
        needsAcceptListBeans.remove(0);
        needsAcceptListBeans.add(article);
        for (int i = 0; i < 2; i++) {
            addContentView(i);
        }
    }

    /* */

    /**
     * 取消滚动
     */
    public void cancelAuto() {
        mHandler.removeMessages(0);
    }

    @SuppressLint("SetTextI18n")
    private void addContentView(int position) {
        ViewHolder mHolder;
        if (position >= getChildCount()) {
            mHolder = new ViewHolder();
            View v = View.inflate(getContext(), R.layout.item_view, null);
            mHolder.mTitleText = v.findViewById(R.id.id_text_title);
            mHolder.mTimeText = v.findViewById(R.id.id_text_time);
            mHolder.mImageView = v.findViewById(R.id.imageView);
            v.setTag(mHolder);
            addView(v, LayoutParams.MATCH_PARENT, ComUtil.dip2px(mContext, (int) HEIGHT));
        } else {
            mHolder = (ViewHolder) getChildAt(position).getTag();
        }
        if (needsAcceptListBeans != null) {
            if (needsAcceptListBeans.size() > 0) {
                NeedsAcceptListBean mListBean = needsAcceptListBeans.get(position);
                if (!TextUtils.isEmpty(mListBean.getRealName())) {
                    mHolder.mTitleText.setText(ComUtil.getHideUserName(mListBean.getRealName()) + "刚刚接了一个" + mListBean.getCategoryName() + "单");
                } else {
                    mHolder.mTitleText.setText(ComUtil.getMobile(mListBean.getMobile()) + "刚刚接了一个" + mListBean.getCategoryName() + "单");
                }
                GlideImage.getInstance().loadImage(mContext, mListBean.getAvatarUrl(), R.mipmap.user_shape_icon, mHolder.mImageView);
            }
        }
    }

    private static class ViewHolder {
        TextView mTitleText;
        TextView mTimeText;
        ShapeableImageView mImageView;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(@NotNull android.os.Message msg) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
            smoothScrollBy(0, ComUtil.dip2px(mContext, (int) HEIGHT));
            if (needsAcceptListBeans != null) {
                if (needsAcceptListBeans.size() > 0) {
                    resetView();
                }
            }
        }

    };

    // 调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        // 设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, dy, 1500);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();

        }
        super.computeScroll();
    }
}
