package com.xczn.smos.event;

/**
 * @Author zhangxiao
 * @Date 2018/3/9 0009
 * @Comment
 */

public class AlarmPostEvent {

    private int number;

    public AlarmPostEvent(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
