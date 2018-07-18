package com.xczn.smos.entity;

import java.io.Serializable;

/**
 * Created by zhangxiao
 * Date on 2018/5/15.
 */
public class User implements Serializable{
    /**
     * userId : admin12345
     * name : 张三
     * type : admin
     * phone : 13454658679
     * address : 河南
     * timeCreate : 2017-10-24 06:31:02
     */

    private String userId;
    private String name;
    private String type;
    private String phone;
    private String address;
    private String timeCreate;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }
}
