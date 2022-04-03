package com.dingdingyijian.ddyj.event;

import com.jeremyliao.liveeventbus.core.LiveEvent;

/**
 * @author: tyh
 * @date: 2022/4/4 0:11
 * @description:
 */
public class LocationEvent implements LiveEvent {

    private double latitude;
    private double longitude;

    public LocationEvent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
