package com.dingdingyijian.ddyj.mapview;

import android.content.Context;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.dingdingyijian.ddyj.utils.ConstantOther;
import com.dingdingyijian.ddyj.utils.Logger;
import com.dingdingyijian.ddyj.utils.PreferenceUtil;

/**
 * 高德地图定位
 */
public class AMapLocationUtils implements AMapLocationListener, LocationSource, AMap.OnCameraChangeListener {

    private static AMapLocationUtils instance;
    private Context mContext;
    /**
     * 高德地图相关
     */
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private MyLocationStyle myLocationStyle;
    private AMap aMap;
    private double latitude = 0;
    private double longitude = 0;
    private MapView mMapView;


    public AMapLocationUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static AMapLocationUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (AMapLocationUtils.class) {
                if (instance == null) {
                    instance = new AMapLocationUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化创建地图
     *
     * @param savedInstanceState
     */
    public void initMapView(Bundle savedInstanceState, MapView mapView) {
        this.mMapView = mapView;
        MapsInitializer.updatePrivacyShow(mContext, true, true);
        MapsInitializer.updatePrivacyAgree(mContext, true);
        mapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mapView.getMap();
            ComUtil.setMapStyleFile(mContext, aMap);
            UiSettings uiSettings = aMap.getUiSettings();
            uiSettings.setLogoBottomMargin(-50);//隐藏logo
            // 设置定位监听
            aMap.setLocationSource(this);
            //取消地图缩放按钮
            uiSettings.setZoomControlsEnabled(false);
            // 是否显示定位按钮
            uiSettings.setMyLocationButtonEnabled(false);
            aMap.setOnCameraChangeListener(this); //// 添加移动地图事件监听器
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            //设置是否显示定位小蓝点
            myLocationStyle.showMyLocation(false);
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.setMyLocationEnabled(true);
            //设置定位蓝点的Style
            aMap.setMyLocationStyle(myLocationStyle);
        }
    }

    /**
     * 开启定位
     */
    public void startLocalService() {
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

    /**
     * 定位回调
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            //定位成功
            String cityAddress = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict();
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_PROVINCE, aMapLocation.getProvince()); //省
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_DISTRICT, aMapLocation.getDistrict()); //区
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LONGITUDE, longitude + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LATITUDE, latitude + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADDRESS, aMapLocation.getAddress() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_ADCODE, aMapLocation.getAdCode() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict());
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY, aMapLocation.getCity() + "");
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_CITY_REGADDRESS, cityAddress);
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_MY_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity());
            PreferenceUtil.getInstance().commitString(ConstantOther.KEY_APP_LAST_SHORT_ADDRESS, aMapLocation.getProvince() + aMapLocation.getCity());
            PreferenceUtil.getInstance().commitString(ConstantOther.NEW_MODE_ADDRESS_TITLE, aMapLocation.getStreet() + aMapLocation.getStreetNum());
            //如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
            if (isFirstLoc) {
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);
                Logger.d("","高德地图定位成功");
                isFirstLoc = false;
            }

        } else {
            //定位失败
            Logger.e("", "高德地图定位失败============》" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());

        }

    }


    /**
     * 回到当前位置
     */
    public void moveCamera() {
        if (latitude != 0 && longitude != 0) {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
        }
    }


    /**
     * 激活定位
     *
     * @param onLocationChangedListener
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
     * 监听地图移动完成
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }


    public void onResume() {
        mMapView.onResume();
    }

    public void onPause() {
        mMapView.onPause();
    }

    public void onDestroy() {
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }
}
