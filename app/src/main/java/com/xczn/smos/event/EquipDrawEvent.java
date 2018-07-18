package com.xczn.smos.event;

import com.xczn.smos.entity.Equipment1;

/**
 * Created by zhangxiao
 * Date on 2018/6/29.
 */
public class EquipDrawEvent {
    private Equipment1 equipment1;

    public EquipDrawEvent(Equipment1 equipment1) {
        this.equipment1 = equipment1;
    }

    public Equipment1 getEquipment1() {
        return equipment1;
    }
}
