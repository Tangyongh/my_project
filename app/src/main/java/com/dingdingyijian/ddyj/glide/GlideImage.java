package com.dingdingyijian.ddyj.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

/**
 * @author tyh
 * @date 2020/6/22
 */
public class GlideImage {

    private GlideImage() {

    }

    private static GlideImage instance;

    public static GlideImage getInstance() {
        if (instance == null) {
            synchronized (GlideImage.class) {
                if (instance == null) {
                    instance = new GlideImage();
                }
            }
        }
        return instance;
    }

    /**
     * 加载图片
     *
     * @param context     上下文
     * @param url         要加载的图片
     * @param placeholder 占位图
     * @param imageView   显示图片控件
     */
    public void loadImage(Context context, Object url, int placeholder, ImageView imageView) {
        if (!ComUtil.isDestroy((RxAppCompatActivity) context)) {
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fallback(placeholder)
                    .into(imageView);
        }
    }

    public void loadImages(Context context, Object url, int placeholder, ImageView imageView) {
        if (!ComUtil.isDestroy((RxAppCompatActivity) context)) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .centerCrop()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fallback(placeholder)
                    .into(imageView);
        }
    }


    /**
     * 加载圆角图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadBannerImage(Context context, Object url, ImageView imageView) {
        if (!ComUtil.isDestroy((RxAppCompatActivity) context)) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(imageView);
        }
    }


    /**
     * @param context
     * @param url
     * @param imageView
     * @param iv_close_ad
     */
    public void loadImageProgress(Context context, Object url, ImageView imageView, ImageView iv_close_ad) {
        if (!ComUtil.isDestroy((RxAppCompatActivity) context)) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            iv_close_ad.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(imageView);
        }
    }


    public void loadImage(Context context, Object url, ImageView imageView) {
        if (!ComUtil.isDestroy((RxAppCompatActivity) context)) {
            GlideApp.with(context)
                    .load(url)
                    .into(imageView);
        }
    }
}
