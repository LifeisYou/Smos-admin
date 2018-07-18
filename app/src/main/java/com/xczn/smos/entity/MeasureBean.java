package com.xczn.smos.entity;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/11.
 */
public class MeasureBean {

    /**
     * number : 66
     * listYc : [{"mesname":"Ia","value":"0","time":"1899-12-30 00:00:00.000","disappear":"0"}]
     * * listYx : [{"mesname":"控制回路异常","value":"0","time":"1899-12-30 00:00:00.000","disappear":"0"}]
     * * listYm : [{"mesname":"正向有功脉冲","value":"0","time":"1899-12-30 00:00:00.000","disappear":"0"}]
     * * listSet : [{"mesname":"零流加速延时","value":"","max":"0","min":"0"}]
     */

    private String number;
    private List<ListYcBean> listYc;
    private List<ListYxBean> listYx;
    private List<ListYmBean> listYm;
    private List<ListSetBean> listSet;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<ListYcBean> getListYc() {
        return listYc;
    }

    public void setListYc(List<ListYcBean> listYc) {
        this.listYc = listYc;
    }

    public List<ListYxBean> getListYx() {
        return listYx;
    }

    public void setListYx(List<ListYxBean> listYx) {
        this.listYx = listYx;
    }

    public List<ListYmBean> getListYm() {
        return listYm;
    }

    public void setListYm(List<ListYmBean> listYm) {
        this.listYm = listYm;
    }

    public List<ListSetBean> getListSet() {
        return listSet;
    }

    public void setListSet(List<ListSetBean> listSet) {
        this.listSet = listSet;
    }

    public static class ListYcBean {
        /**
         * mesname : Ia
         * value : 0
         * time : 1899-12-30 00:00:00.000
         * disappear : 0
         */

        private String mesname;
        private String value;
        private String time;
        private String disappear;

        public String getMesname() {
            return mesname;
        }

        public void setMesname(String mesname) {
            this.mesname = mesname;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDisappear() {
            return disappear;
        }

        public void setDisappear(String disappear) {
            this.disappear = disappear;
        }
    }

    public static class ListYxBean {
        /**
         * mesname : 控制回路异常
         * value : 0
         * time : 1899-12-30 00:00:00.000
         * disappear : 0
         */

        private String mesname;
        private String value;
        private String time;
        private String disappear;

        public String getMesname() {
            return mesname;
        }

        public void setMesname(String mesname) {
            this.mesname = mesname;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDisappear() {
            return disappear;
        }

        public void setDisappear(String disappear) {
            this.disappear = disappear;
        }
    }
    public static class ListYmBean {
        /**
         * mesname : 正向有功脉冲
         * value : 0
         * time : 1899-12-30 00:00:00.000
         * disappear : 0
         */

        private String mesname;
        private String value;
        private String time;
        private String disappear;

        public String getMesname() {
            return mesname;
        }

        public void setMesname(String mesname) {
            this.mesname = mesname;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDisappear() {
            return disappear;
        }

        public void setDisappear(String disappear) {
            this.disappear = disappear;
        }
    }
    public static class ListSetBean {
        /**
         * mesname:零流加速延时
         * value:
         * max:0
         * min:0
         */

        private String mesname;
        private String value;
        private String max;
        private String min;

        public String getMesname() {
            return mesname;
        }

        public void setMesname(String mesname) {
            this.mesname = mesname;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String disappear) {
            this.min = min;
        }
    }

}
