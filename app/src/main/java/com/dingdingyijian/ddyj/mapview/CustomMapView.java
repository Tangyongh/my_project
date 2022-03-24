package com.dingdingyijian.ddyj.mapview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.mapview.listener.LocationCallback;
import com.dingdingyijian.ddyj.mapview.listener.onMapViewChangeCallBack;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/22
 * @describe: 自定义封装高德地图
 */
public class CustomMapView extends MapView implements LocationSource, AMapLocationListener, AMap.OnCameraChangeListener {

    private Context mContext;
    private AMap mAMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyLocationStyle myLocationStyle;
    private boolean isFirstLoc = true;
    private double latitude = 0;
    private double longitude = 0;
    private CustomMapView mCustomMapView;

    public CustomMapView(Context context) {
        super(context);

    }

    public CustomMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    public CustomMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);

    }


    public void init(Context context, Bundle savedInstanceState,CustomMapView customMapView) {
        this.mCustomMapView = customMapView;
        mContext = context.getApplicationContext();
        MapsInitializer.updatePrivacyShow(mContext, true, true);
        MapsInitializer.updatePrivacyAgree(mContext, true);
        this.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = customMapView.getMap();
            ComUtil.setMapStyleFile(mContext, mAMap);
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setLogoBottomMargin(-50);//隐藏logo
            // 设置定位监听
            mAMap.setLocationSource(this);
            //取消地图缩放按钮
            uiSettings.setZoomControlsEnabled(false);
            // 是否显示定位按钮
            uiSettings.setMyLocationButtonEnabled(false);
            mAMap.setOnCameraChangeListener(this); //// 添加移动地图事件监听器
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            //设置是否显示定位小蓝点
            myLocationStyle.showMyLocation(false);
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            mAMap.setMyLocationEnabled(true);
            //设置定位蓝点的Style
            mAMap.setMyLocationStyle(myLocationStyle);
        }
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(mContext);
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void activate(){
        activate(mListener);
    }


    /**
     * 回到当前位置
     */
    public void moveCamera() {
        if (latitude != 0 && longitude != 0) {
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 定位回调
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            latitude = aMapLocation.getLatitude();
            longitude = aMapLocation.getLongitude();
            //定位成功
            String cityAddress = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict();
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_PROVINCE, aMapLocation.getProvince()); //省
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_DISTRICT, aMapLocation.getDistrict()); //区
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LONGITUDE, aMapLocation.getLongitude() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LATITUDE, aMapLocation.getLatitude() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADDRESS, aMapLocation.getAddress() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADCODE, aMapLocation.getAdCode() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict());
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY, aMapLocation.getCity() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY_REGADDRESS, cityAddress);
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_MY_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity());
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LAST_SHORT_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity());
            PreferenceUtil.getInstance().commitString(ConstantOther.NEW_MODE_ADDRESS_TITLE, aMapLocation.getStreet() + aMapLocation.getStreetNum());
            if (isFirstLoc) {
                //设置缩放级别
                mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                //将地图移动到定位点
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);
                //定位成功回调
                mLocationCallback.onLocationSuccess(aMapLocation);
                isFirstLoc = false;
            }

        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Logger.d("","高德地图定位失败错误码===="+aMapLocation.getErrorCode()+",错误信息=="+aMapLocation.getErrorInfo());
        }
    }

    private LocationCallback mLocationCallback = null;
    private onMapViewChangeCallBack mChangeCallBack = null;
    public void setLocationCallback(LocationCallback listener) {
        this.mLocationCallback = listener;
    }
    public void setonMapViewChangeCallBack(onMapViewChangeCallBack listener) {
        this.mChangeCallBack = listener;
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    /**
     * 地图移动停止后的回调
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (cameraPosition != null && !TextUtils.isEmpty(cameraPosition.target.toString())) {
            LatLngEntity latLngEntity = new LatLngEntity(cameraPosition.target.latitude, cameraPosition.target.longitude);
            mChangeCallBack.onCameraChangeFinish(latLngEntity);
        }
    }

}
