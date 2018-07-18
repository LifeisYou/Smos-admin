package com.xczn.smos.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author zhangxiao
 * @Date 2018/4/25 0025
 * @Comment
 */

public class UsersPinyin implements Parcelable {

    private String index;
    private String userId;
    private String name;
    private String type;
    private String phone;
    private String namePinyin;


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.index);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.phone);
        dest.writeString(this.namePinyin);
    }

    public UsersPinyin() {
    }

    protected UsersPinyin(Parcel in) {
        this.index = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.phone = in.readString();
        this.namePinyin = in.readString();
    }

    public static final Creator<UsersPinyin> CREATOR = new Creator<UsersPinyin>() {
        @Override
        public UsersPinyin createFromParcel(Parcel source) {
            return new UsersPinyin(source);
        }

        @Override
        public UsersPinyin[] newArray(int size) {
            return new UsersPinyin[size];
        }
    };
}
