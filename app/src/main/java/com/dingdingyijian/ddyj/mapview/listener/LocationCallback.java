package com.dingdingyijian.ddyj.mapview.listener;

import com.amap.api.location.AMapLocation;

/**
 * @author: DDYiJian
 * @time: 2022/3/22
 * @describe: 定位成功回调
 */
public interface LocationCallback {
    void onLocationSuccess(AMapLocation aMapLocation);
}
