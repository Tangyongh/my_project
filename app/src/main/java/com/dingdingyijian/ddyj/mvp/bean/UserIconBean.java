package com.dingdingyijian.ddyj.mvp.bean;

/**
 * @author: tyh
 * @date: 2022/3/22 23:40
 * @description:
 */
public class UserIconBean {


    private double lon;
    private int id;
    private double lat;
    private String categoryType;


    public UserIconBean(double lon, double lat, String categoryType) {
        this.lon = lon;
        this.lat = lat;
        this.categoryType = categoryType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
