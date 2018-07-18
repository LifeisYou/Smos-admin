package com.xczn.smos.entity;

/**
 * Created by zhangxiao
 * Date on 2018/5/18.
 */
public class AlarmReceive {

    /**
     * alarm_id : 12355
     * alarm_time : 43091.4412724537
     * equipment : 1236
     * message : 网络断开
     * level : 一般
     */

    private String alarm_id;
    private double alarm_time;
    private String equipment;
    private String message;
    private String level;

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public double getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(double alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
