package com.xczn.smos.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhangxiao
 * Date on 2018/5/17.
 */
public class Equipment1 implements Parcelable {
    /**
     * type : 发电设备
     * equipname : 逆变器
     * equipid : 1001
     * equiplongitude : 113.868
     * equiplatitude : 34.098
     * equipdescription :
     * stationid :
     * bayid :
     */

    private String type;
    private String equipname;
    private String equipid;
    private String equiplongitude;
    private String equiplatitude;
    private String equipdescription;
    private String stationid;
    private String bayid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public String getEquipid() {
        return equipid;
    }

    public void setEquipid(String equipid) {
        this.equipid = equipid;
    }

    public String getEquiplongitude() {
        return equiplongitude;
    }

    public void setEquiplongitude(String equiplongitude) {
        this.equiplongitude = equiplongitude;
    }

    public String getEquiplatitude() {
        return equiplatitude;
    }

    public void setEquiplatitude(String equiplatitude) {
        this.equiplatitude = equiplatitude;
    }

    public String getEquipdescription() {
        return equipdescription;
    }

    public void setEquipdescription(String equipdescription) {
        this.equipdescription = equipdescription;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getBayid() {
        return bayid;
    }

    public void setBayid(String bayid) {
        this.bayid = bayid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.equipname);
        dest.writeString(this.equipid);
        dest.writeString(this.equiplongitude);
        dest.writeString(this.equiplatitude);
        dest.writeString(this.equipdescription);
        dest.writeString(this.stationid);
        dest.writeString(this.bayid);
    }

    public Equipment1() {
    }

    protected Equipment1(Parcel in) {
        this.type = in.readString();
        this.equipname = in.readString();
        this.equipid = in.readString();
        this.equiplongitude = in.readString();
        this.equiplatitude = in.readString();
        this.equipdescription = in.readString();
        this.stationid = in.readString();
        this.bayid = in.readString();
    }

    public static final Parcelable.Creator<Equipment1> CREATOR = new Parcelable.Creator<Equipment1>() {
        @Override
        public Equipment1 createFromParcel(Parcel source) {
            return new Equipment1(source);
        }

        @Override
        public Equipment1[] newArray(int size) {
            return new Equipment1[size];
        }
    };
}
