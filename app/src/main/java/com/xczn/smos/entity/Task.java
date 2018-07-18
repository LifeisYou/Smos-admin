package com.xczn.smos.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/3/5 0005
 * @Comment
 */

public class Task implements Serializable {


    /**
     * task : {"taskId":"1236","type":"报警","publisher":"1234","receiver":"12345","equipment":"设备1","message":"消息1","status":0,"timeDeadline":"2017-10-24 06:31:02","timePublish":"2017-10-24 06:31:02","msgReceive":"","timeReceive":"","msgProcess":"","timeProcess":"","msgSummary":"","timeSummary":"","alarmId":123146}
     * pictures : ["12341_2.jpg","12341_3.jpg","12341_4.jpg","12341_5.jpg","12343_0.jpg","12343_2.jpg","12343_3.jpg","12343_4.jpg","1236_0.jpg","1236_1.jpg"]
     */

    private TaskBean task;
    private List<String> pictures;

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public static class TaskBean {
        /**
         * taskId : 1236
         * type : 报警
         * publisher : 1234
         * receiver : 12345
         * equipment : 设备1
         * message : 消息1
         * status : 0
         * timeDeadline : 2017-10-24 06:31:02
         * timePublish : 2017-10-24 06:31:02
         * msgReceive :
         * timeReceive :
         * msgProcess :
         * timeProcess :
         * msgSummary :
         * timeSummary :
         * alarmId : 123146
         */

        private String taskId;
        private String type;
        private String publisher;
        private String receiver;
        private String equipment;
        private String message;
        private int status;
        private String timeDeadline;
        private String timePublish;
        private String msgReceive;
        private String timeReceive;
        private String msgProcess;
        private String timeProcess;
        private String msgSummary;
        private String timeSummary;
        private String alarmId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getMsgReceive() {
            return msgReceive;
        }

        public void setMsgReceive(String msgReceive) {
            this.msgReceive = msgReceive;
        }

        public String getTimeReceive() {
            return timeReceive;
        }

        public void setTimeReceive(String timeReceive) {
            this.timeReceive = timeReceive;
        }

        public String getMsgProcess() {
            return msgProcess;
        }

        public void setMsgProcess(String msgProcess) {
            this.msgProcess = msgProcess;
        }

        public String getTimeProcess() {
            return timeProcess;
        }

        public void setTimeProcess(String timeProcess) {
            this.timeProcess = timeProcess;
        }

        public String getMsgSummary() {
            return msgSummary;
        }

        public void setMsgSummary(String msgSummary) {
            this.msgSummary = msgSummary;
        }

        public String getTimeSummary() {
            return timeSummary;
        }

        public void setTimeSummary(String timeSummary) {
            this.timeSummary = timeSummary;
        }

        public String getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(String alarmId) {
            this.alarmId = alarmId;
        }
    }
}
