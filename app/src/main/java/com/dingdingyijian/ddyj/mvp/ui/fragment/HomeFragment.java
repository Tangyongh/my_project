package com.dingdingyijian.ddyj.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.dingdingyijian.ddyj.base.BaseFragment;
import com.dingdingyijian.ddyj.databinding.FragmentHomeBinding;
import com.dingdingyijian.ddyj.mapview.LocationUtils;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.dingdingyijian.ddyj.mvp.contract.HomeFragmentContract;
import com.dingdingyijian.ddyj.mvp.data.DataInfoResult;
import com.dingdingyijian.ddyj.mvp.presenter.HomeFragmentPresenter;
import com.dingdingyijian.ddyj.utils.Logger;
import com.lk.mapsdk.base.mapapi.model.LatLng;
import com.lk.mapsdk.base.platform.mapapi.util.CoordUtil;
import com.lk.mapsdk.map.mapapi.camera.MapStatus;
import com.lk.mapsdk.map.mapapi.camera.MapStatusUpdateFactory;
import com.lk.mapsdk.map.mapapi.map.LKMap;
import com.lk.mapsdk.search.mapapi.base.PoiInfo;
import com.lk.mapsdk.search.mapapi.reversegeocoder.ReverseGeoCoder;
import com.lk.mapsdk.search.mapapi.reversegeocoder.ReverseGeoCoderOptions;

import java.util.List;

/**
 * @author: tyh
 * @time: 2022/3/11
 * @describe: 首页
 */
public class HomeFragment extends BaseFragment<HomeFragmentContract.View, HomeFragmentContract.Presenter, FragmentHomeBinding> implements HomeFragmentContract.View, LKMap.OnMapMoveListener, LKMap.OnDidFinishLoadingMapListener {

    private LKMap mLkMap;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }


    @Override
    public HomeFragmentContract.Presenter createPresenter() {

        return new HomeFragmentPresenter(mContext);
    }

    @Override
    public HomeFragmentContract.View createView() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //初始化创建地图
        initMap(savedInstanceState);
        //banner图
        getPresenter().setBanner();
        //请求首页接单滚动信息
        getPresenter().getHomeNeedsNotice(new NeedsAcceptListBean());
        //待处理信息
        getPresenter().setNeedsCount(getBinding().tvMissNeeds, getBinding().tvMyNeeds, getBinding().tvSendNeeds);
        //登录成功或者退出登录的时候刷新下接口
        getPresenter().onEvent(getBinding().tvMissNeeds, getBinding().tvMyNeeds, getBinding().tvSendNeeds);

    }


    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public void getBannerResult(BannerBean bannerBean) {
        //banner请求数据
        DataInfoResult.getBannerResult(getBinding().banner, bannerBean);
    }

    @Override
    public void getHomeNeedsNoticeResult(List<NeedsAcceptListBean> acceptListBean) {
        //首页接单滚动信息请求数据
        if (acceptListBean == null) return;
        if (acceptListBean.size() > 0) {
            getBinding().cardview.setVisibility(View.VISIBLE);
            getBinding().scrollView.setData(acceptListBean);
        } else {
            getBinding().cardview.setVisibility(View.GONE);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getNeedsCountResult(NeedsCountBean needsCountBean) {
        if (needsCountBean == null) return;
        getBinding().tvMissNeeds.setText("待处理" + needsCountBean.getWaitCount() + "单,");
        getBinding().tvMyNeeds.setText("接了" + needsCountBean.getReceiveCount() + "单。");
        getBinding().tvSendNeeds.setText("点击查看详情");
    }

    @Override
    public void getMapViewIconResult(List<UserIconBean> userIconBean) {

    }


    //初始化高德地图
    private void initMap(Bundle savedInstanceState) {
        getBinding().mapView.onCreate(savedInstanceState);
        mLkMap = getBinding().mapView.getMap();
        LatLng center = LocationUtils.getInstance().getLatLng();
        LatLng latLng = CoordUtil.fromWGS84ToGCJ02(center);
        mLkMap.setMapStatus(MapStatusUpdateFactory.buildUpdateByCenterAndZoom(latLng, 16));
        mLkMap.setOnMoveListener(this);
        mLkMap.setOnDidFinishLoadingMapListener(this);
    }




    @Override
    public void onResume() {
        super.onResume();
        getBinding().mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getBinding().mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getBinding().banner.stopAutoPlay();
        getBinding().mapView.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        getBinding().banner.startAutoPlay();
        getBinding().mapView.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getBinding().mapView.onDestroy();
        mLkMap.removeOnMoveListener(this);
        mLkMap.removeOnDidFinishLoadingMapListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getBinding().mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onMoveBegin() {

    }

    @Override
    public void onMove() {

    }

    @Override
    public void onMoveEnd() {
        //地图移动完成
        MapStatus mapStatus = mLkMap.getMapStatus();
        double longitude = mapStatus.target.longitude;
        double latitude = mapStatus.target.latitude;
        LatLng targetLatLng = new LatLng(latitude, longitude);
        ReverseGeoCoder search = new ReverseGeoCoder();
        ReverseGeoCoderOptions options = new ReverseGeoCoderOptions();
        options.setLocation(targetLatLng);
        // 发起检索
        search.reverseGeoCoderRequest(options, reverseGeoCoderResult -> {
            if (reverseGeoCoderResult.getPoiInfoList() == null) return;
            List<PoiInfo> poiInfoList = reverseGeoCoderResult.getPoiInfoList();
            if (poiInfoList.size() > 0) {
                PoiInfo poiInfo = poiInfoList.get(0);
                Logger.d("LocationManager,onMoveEnd", "latitude=========" + poiInfo.getCoordinate().latitude);
                Logger.d("LocationManager,onMoveEnd", "latitude=========" + poiInfo.getCoordinate().longitude);
                Logger.d("LocationManager,onMoveEnd", "getCountryName=========" + poiInfo.getProvinceName());
                Logger.d("LocationManager,onMoveEnd", "getCityName=========" + poiInfo.getCityName());
                Logger.d("LocationManager,onMoveEnd", "getCountyName=========" + poiInfo.getCountyName());
                Logger.d("LocationManager,onMoveEnd", "getStreet=========" + poiInfo.getStreet());
                Logger.d("LocationManager,onMoveEnd", "getName=========" + poiInfo.getName());
                String address = poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getCountyName() + poiInfo.getStreet() + poiInfo.getName();
                Logger.d("LocationManager,onMoveEnd", "address=========" + address);
            }
        });

    }

    @Override
    public void onDidFinishLoadingMap() {
        //地图加载完成
        MapStatus mapStatus = mLkMap.getMapStatus();
        double longitude = mapStatus.target.longitude;
        double latitude = mapStatus.target.latitude;
        LatLng targetLatLng = new LatLng(latitude, longitude);
        ReverseGeoCoder search = new ReverseGeoCoder();
        ReverseGeoCoderOptions options = new ReverseGeoCoderOptions();
        options.setLocation(targetLatLng);
        // 发起检索
        search.reverseGeoCoderRequest(options, reverseGeoCoderResult -> {
            if (reverseGeoCoderResult.getPoiInfoList() == null) return;
            List<PoiInfo> poiInfoList = reverseGeoCoderResult.getPoiInfoList();
            if (poiInfoList.size() > 0) {
                PoiInfo poiInfo = poiInfoList.get(0);
                Logger.d("LocationManager,onDidFinishLoadingMap", "latitude=========" + poiInfo.getCoordinate().latitude);
                Logger.d("LocationManager,onDidFinishLoadingMap", "latitude=========" + poiInfo.getCoordinate().longitude);
                Logger.d("LocationManager,onDidFinishLoadingMap", "getCountryName=========" + poiInfo.getProvinceName());
                Logger.d("LocationManager,onDidFinishLoadingMap", "getCityName=========" + poiInfo.getCityName());
                Logger.d("LocationManager,onDidFinishLoadingMap", "getCountyName=========" + poiInfo.getCountyName());
                Logger.d("LocationManager,onDidFinishLoadingMap", "getStreet=========" + poiInfo.getStreet());
                Logger.d("LocationManager,onDidFinishLoadingMap", "getName=========" + poiInfo.getName());
                String address = poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getCountyName() + poiInfo.getStreet() + poiInfo.getName();
                Logger.d("LocationManager,onDidFinishLoadingMap", "address=========" + address);
            }
        });
    }
}
