package com.xczn.smos.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangxiao
 * Date on 2018/5/10.
 */
public class LoginBean implements Parcelable{

    /**
     * username : 12345
     * name : 李四admin
     * phone : 135_4325_5678
     * type : admin
     * address : 许昌
     */

    private String username;
    private String name;
    private String phone;
    private String type;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.type);
        dest.writeString(this.address);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.username = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.type = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
