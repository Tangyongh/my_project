package com.dingdingyijian.ddyj.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.base.BaseFragment;
import com.dingdingyijian.ddyj.databinding.FragmentHomeBinding;
import com.dingdingyijian.ddyj.mapview.GeoCoderUtil;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;
import com.dingdingyijian.ddyj.mvp.contract.HomeFragmentContract;
import com.dingdingyijian.ddyj.mvp.data.DataInfoResult;
import com.dingdingyijian.ddyj.mvp.data.MapViewResult;
import com.dingdingyijian.ddyj.mvp.presenter.HomeFragmentPresenter;
import com.dingdingyijian.ddyj.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: tyh
 * @time: 2022/3/11
 * @describe: 首页
 */
public class HomeFragment extends BaseFragment<HomeFragmentContract.View, HomeFragmentContract.Presenter, FragmentHomeBinding> implements HomeFragmentContract.View {

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
        if (userIconBean == null) return;
        if (userIconBean.size() >0){
            setMapData(userIconBean);
        }
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
    }

    @Override
    public void onStart() {
        super.onStart();
        getBinding().banner.startAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getBinding().mapView.onDestroy();
        getBinding().mapView.deactivate();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getBinding().mapView.onSaveInstanceState(outState);
    }

    //初始化高德地图
    private void initMap(Bundle savedInstanceState) {
        //初始化地图
        MapViewResult.getMapView(mContext, savedInstanceState, getBinding().mapView, getBinding().iconLocation);

        //定位成功回调
        getBinding().mapView.setLocationCallback(aMapLocation -> {
            //定位成功
            Logger.d("", "定位成功==========" + aMapLocation.getAddress());
        });

        //地图移动后的回调
        getBinding().mapView.setonMapViewChangeCallBack(latLngEntity -> {
            Logger.d("onCameraChangeFinish", "地图移动后的经纬度getLatitude==========" + latLngEntity.getLatitude());
            Logger.d("onCameraChangeFinish", "地图移动后的经纬度getLongitude==========" + latLngEntity.getLongitude());
            //移动地图之后加载地图上的头像
            UserIconBean userIconBean = new UserIconBean(latLngEntity.getLongitude(), latLngEntity.getLatitude(), "1");
            getPresenter().getMapViewIcon(userIconBean);
        });
    }


    /**
     * 添加多个marker
     */
    public void setMapData(List<UserIconBean> userIconBean) {
        List<UserIconBean> list = new ArrayList<>();
        for (UserIconBean bean : userIconBean) {
            list.add(bean);
        }
        getBinding().mapView.getMap().clear(true);
        if (list.size() > 0) {
            for (int i = 0; i < userIconBean.size(); i++) {
                View markerViews = LayoutInflater.from(mContext).inflate(R.layout.marker_image, null, false);
                getBinding().mapView.getMap().addMarker(new MarkerOptions()
                        .position(new LatLng(list.get(i).getLat(), list.get(i).getLon()))//设置经度
                        .setFlat(false) // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                        .draggable(false) //设置Marker可拖动
                        .icon(BitmapDescriptorFactory.fromView(markerViews)));
            }
        }
    }
}
