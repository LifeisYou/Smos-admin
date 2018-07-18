package com.xczn.smos.entity;

/**
 * Created by zhangxiao
 * Date on 2018/5/10.
 */
public class TaskStepBean {

    private String day;

    private String time;

    private String publisher;

    private String receiver;

    private String message;

    private int status;

    public TaskStepBean(String day_time, String publisher, String receiver, String message, int status) {
        String[] dayAndTime = day_time.split(" ");
        this.day = dayAndTime[0];
        this.time = dayAndTime[1];
        this.publisher = publisher;
        this.receiver = receiver;
        this.message = message;
        this.status = status;

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
