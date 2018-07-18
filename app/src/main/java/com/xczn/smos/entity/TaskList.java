package com.xczn.smos.entity;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 */
public class TaskList {


    /**
     * taskId : 123456789
     * publisher : 1234
     * receiver : 12345
     * equipment : 设备
     * message : 信息
     * status : 1
     * timeDeadline : 2017-10-24 06:31:02
     * timePublish : 2017-10-24 06:31:02
     */

    private String taskId;
    private String publisher;
    private String receiver;
    private String equipment;
    private String message;
    private int status;
    private String timeDeadline;
    private String timePublish;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeDeadline() {
        return timeDeadline;
    }

    public void setTimeDeadline(String timeDeadline) {
        this.timeDeadline = timeDeadline;
    }

    public String getTimePublish() {
        return timePublish;
    }

    public void setTimePublish(String timePublish) {
        this.timePublish = timePublish;
    }
}
