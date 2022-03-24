package com.dingdingyijian.ddyj.mapview;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

/**
 * Created by DDYiJian on 2019/4/13.
 * 高德地图搜索POI
 */

public class GeoCoderUtil implements GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    private GeocodeSearch geocodeSearch;
    private GeoCoderAddressListener geoCoderAddressListener;
    private GeoCoderLatLngListener geoCoderLatLngListener;


    private static GeoCoderUtil geoCoderUtil;
    private PoiSearch.Query mQuery;
    private List<SuggestionCity> mSuggestionCitys;

    public static GeoCoderUtil getInstance(Context context) {
        if (null == geoCoderUtil) {
            geoCoderUtil = new GeoCoderUtil(context);
        }
        return geoCoderUtil;
    }

    private GeoCoderUtil(Context context) {
        try {
            geocodeSearch = new GeocodeSearch(context);
            geocodeSearch.setOnGeocodeSearchListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 经纬度转地址描述
     **/
    public void geoAddress(LatLngEntity latLngEntity, GeoCoderAddressListener geoCoderAddressListener) {
        if (latLngEntity == null) {
            geoCoderAddressListener.onAddressResult("");
            return;
        }
        this.geoCoderAddressListener = geoCoderAddressListener;

        LatLonPoint latLonPoint = new LatLonPoint(latLngEntity.getLatitude(), latLngEntity.getLongitude());
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 10, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求

    }

    /**
     * 地址描述转经纬度
     **/
    public void geoLatLng(String cityName, String address, GeoCoderLatLngListener geoCoderLatLngListener) {
        if (TextUtils.isEmpty(address)) {
            geoCoderLatLngListener.onLatLngResult(null);
            return;
        }
        this.geoCoderLatLngListener = geoCoderLatLngListener;
        GeocodeQuery query = new GeocodeQuery(address, "");
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode != 1000 || result == null || result.getRegeocodeAddress() == null) {
            geoCoderAddressListener.onAddressResult("");
            return;
        }
        mQuery = new PoiSearch.Query("", "", result.getRegeocodeAddress().getCity());
        mQuery.setPageSize(10); //设置每页返回10条
        mQuery.setPageNum(0);// 设置查询第一页

        RegeocodeAddress regeocodeAddress = result.getRegeocodeAddress();
        String addressDesc = regeocodeAddress.getProvince()
                + regeocodeAddress.getCity()
                + regeocodeAddress.getDistrict()
                + regeocodeAddress.getTownship()
                + regeocodeAddress.getStreetNumber().getStreet();
        if (regeocodeAddress.getAois().size() > 0) {
            addressDesc += regeocodeAddress.getAois().get(0).getAoiName();
        }
        geoCoderAddressListener.onAddressResult(addressDesc);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
        if (geocodeResult == null || geocodeResult.getGeocodeAddressList() == null
                || geocodeResult.getGeocodeAddressList().size() <= 0) {
            geoCoderLatLngListener.onLatLngResult(null);
            return;
        }
        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
        geoCoderLatLngListener.onLatLngResult(new LatLngEntity(address.getLatLonPoint()));

    }

    @Override
    public void onPoiSearched(PoiResult result, int i) {
        if (i == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(mQuery)) {// 是否是同一条
                    mSuggestionCitys = result.getSearchSuggestionCitys();
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem item, int i) {

    }

    public interface GeoCoderAddressListener {
        void onAddressResult(String result);
    }

    public interface GeoCoderLatLngListener {
        void onLatLngResult(LatLngEntity latLng);
    }

}
