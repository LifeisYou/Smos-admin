package com.xczn.smos.entity;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/21.
 */
public class Chart {

    /**
     * timetype : 0
     * timestart : 2018-06-20
     * timeend :
     * measuretype :
     * data : [{"time":"2018-06-20 23","value":"0.179"},{"time":"2018-06-20 22","value":"0.13"},{"time":"2018-06-20 21","value":"0.12"},{"time":"2018-06-20 20","value":"0.131"},{"time":"2018-06-20 19","value":"0.175"},{"time":"2018-06-20 18","value":"0.121"},{"time":"2018-06-20 17","value":"0"},{"time":"2018-06-20 15","value":"0.182"},{"time":"2018-06-20 14","value":"0.094"},{"time":"2018-06-20 13","value":"0.071"},{"time":"2018-06-20 12","value":"0"},{"time":"2018-06-20 11","value":"0.026"},{"time":"2018-06-20 10","value":"0.175"}]
     */

    private String timetype;
    private String timestart;
    private String timeend;
    private String measuretype;
    private List<DataBean> data;

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getMeasuretype() {
        return measuretype;
    }

    public void setMeasuretype(String measuretype) {
        this.measuretype = measuretype;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-06-20 23
         * value : 0.179
         */

        private String time;
        private String value;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
