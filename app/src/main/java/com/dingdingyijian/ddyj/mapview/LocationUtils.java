package com.dingdingyijian.ddyj.mapview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.dingdingyijian.ddyj.utils.Logger;
import com.lk.mapsdk.base.mapapi.model.LatLng;

import org.jetbrains.annotations.NotNull;

/**
 * @author: tyh
 * @date: 2022/4/3 19:51
 * @description:
 */
public class LocationUtils {

    private static LocationUtils instance;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;

    public static LocationUtils getInstance() {
        if (instance == null) {
            synchronized (LocationUtils.class) {
                if (instance == null) {
                    instance = new LocationUtils();
                }
            }
        }
        return instance;
    }

    //初始化位置信息
    @SuppressLint("MissingPermission")
    public void startLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS定位
            LocationListener locationListener = getLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
                Logger.d("LocationManager,gps", "mLatitude=========" + mLatitude);
                Logger.d("LocationManager,gps", "mLongitude=========" + mLongitude);
            }
        } else {
            //网络定位
            LocationListener locationListener = getLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                mLatitude = location.getLatitude(); // 经度
                mLongitude = location.getLongitude(); // 纬度
                Logger.d("LocationManager,net", "mLatitude=========" + mLatitude);
                Logger.d("LocationManager,net", "mLongitude=========" + mLongitude);
            }
        }
    }

    @NotNull
    private LocationListener getLocationListener() {
        return new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(@NotNull String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(@NotNull String provider) {

            }

            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(@NotNull Location location) {

            }
        };
    }

    public LatLng getLatLng() {
        return new LatLng(this.mLatitude, this.mLongitude);
    }
}
