package com.dingdingyijian.ddyj.mapview.listener;

import com.amap.api.location.AMapLocation;
import com.dingdingyijian.ddyj.mapview.LatLngEntity;

/**
 * @author: tyh
 * @date: 2022/3/22 23:02
 * @description:
 */
public interface onMapViewChangeCallBack {

    void onCameraChangeFinish(LatLngEntity latLngEntity);
}
