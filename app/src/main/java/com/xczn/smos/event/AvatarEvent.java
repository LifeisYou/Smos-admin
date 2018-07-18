package com.xczn.smos.event;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class AvatarEvent {

    private boolean update;

    public AvatarEvent(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }
}
