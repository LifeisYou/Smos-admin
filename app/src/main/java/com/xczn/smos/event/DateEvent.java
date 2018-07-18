package com.xczn.smos.event;

/**
 * Created by zhangxiao
 * Date on 2018/6/22.
 */
public class DateEvent {
    private String type;
    private String date;

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public DateEvent(String type, String date) {
        this.type = type;
        this.date = date;
    }
}
