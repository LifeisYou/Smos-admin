package com.xczn.smos.entity;

import android.support.v4.app.Fragment;

import java.io.Serializable;


/**
 * Created by zhangxiao
 * Date on 2018/6/7.
 */
public class TaskFragment implements Serializable{
    private Class type;

    private Fragment fragment;

    public TaskFragment(Class type, Fragment fragment) {
        this.type = type;
        this.fragment = fragment;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
