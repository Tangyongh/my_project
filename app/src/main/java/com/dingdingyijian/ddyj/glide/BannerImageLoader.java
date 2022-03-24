package com.dingdingyijian.ddyj.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;


/**
 * 作者：Zhout
 * 时间：2017/12/18 15:01
 * 描述：统一的加载banner图片(各种类型的， 热门banner，视频banner，直播间广告banner,商城banner.....)
 */

public class BannerImageLoader extends ImageLoader {


    //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
    @Override
    public void displayImage(Context context, final Object imgObj, ImageView imageView) {
        imageView.setPadding(25, 0, 25, 0);
        GlideImage.getInstance().loadBannerImage(context, imgObj.toString(), imageView);
    }
}
