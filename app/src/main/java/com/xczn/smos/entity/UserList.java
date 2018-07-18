package com.xczn.smos.entity;

/**
 * Created by zhangxiao
 * Date on 2018/5/15.
 */
public class UserList {

    /**
     * userId : 12345
     * name : 马六
     * type : admin
     * phone : 124335678889
     */

    private String userId;
    private String name;
    private String type;
    private String phone;

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
}
