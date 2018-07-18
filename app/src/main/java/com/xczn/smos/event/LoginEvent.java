package com.xczn.smos.event;

import com.xczn.smos.entity.LoginBean;
import com.xczn.smos.entity.UserList;

/**
 * Created by zhangxiao
 * Date on 2018/5/7.
 */
public class LoginEvent {

    public UserList user;

    public LoginEvent(UserList user){
        this.user = user;
    }
}
