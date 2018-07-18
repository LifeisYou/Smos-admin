package com.xczn.smos.entity;

/**
 * Created by zhangxiao
 * Date on 2018/6/22.
 */
public class StationMsgBean {


    /**
     * station : station
     * name : 场站
     * count : 8
     */

    private String station;
    private String name;
    private int count;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
