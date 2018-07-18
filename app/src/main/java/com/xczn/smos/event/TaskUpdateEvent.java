package com.xczn.smos.event;

/**
 * Created by zhangxiao
 * Date on 2018/6/29.
 */
public class TaskUpdateEvent {

    private int flag;

    public TaskUpdateEvent(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
