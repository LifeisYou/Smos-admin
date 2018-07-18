package com.xczn.smos.event;

/**
 * Created by zhangxiao
 * Date on 2018/6/29.
 */
public class EquipSearchEvent {

    private String equipName;

    public EquipSearchEvent(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }
}
