package com.dingdingyijian.ddyj.mvp.data;

import com.dingdingyijian.ddyj.glide.BannerImageLoader;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.ToastUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/22
 * @describe: com.dingdingyijian.ddyj.mvp
 */
public class DataInfoResult {

    /**
     * banner数据
     * @param banner
     * @param bannerBean
     */
    public static void getBannerResult(Banner banner, BannerBean bannerBean) {
        //banner请求数据
        List<BannerBean.ListBean> list = bannerBean.getList();
        if (list != null) {
            if (list.size() > 0) {
                List<String> listData = new ArrayList<>();
                for (BannerBean.ListBean bean : list) {
                    listData.add(bean.getImageUrl());
                }
                banner.setImageLoader(new BannerImageLoader());
                //设置图片集合
                banner.setImages(listData);
                //设置轮播时间
                banner.setDelayTime(4000);
                banner.setOnBannerListener(position -> {
                    //banner 点击事件
                    ToastUtil.showMsg("点击了======" + position);
                    Logger.d("LiveEventBus", "发送消息====");
                });
                banner.start();
            }
        }
    }
}