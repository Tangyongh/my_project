package com.dingdingyijian.ddyj.mvp.data;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.dingdingyijian.ddyj.mapview.CustomMapView;

/**
 * @author: tyh
 * @date: 2022/3/22 23:14
 * @description:
 */
public class MapViewResult {

    /**
     * 初始化地图
     * @param context
     * @param savedInstanceState
     * @param mapView
     * @param iconLocation
     */
    public static void getMapView(Context context, Bundle savedInstanceState, CustomMapView mapView, ImageView iconLocation) {
        mapView.init(context, savedInstanceState, mapView);
        mapView.startLocation();
        mapView.activate();
        //移动地图后，点击回到当前位置
        iconLocation.setOnClickListener(v -> mapView.moveCamera());
    }
}
