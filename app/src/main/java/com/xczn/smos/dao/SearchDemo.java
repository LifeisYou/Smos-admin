package com.xczn.smos.dao;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by zhangxiao
 * Date on 2018/5/2.
 */

@Entity
public class SearchDemo {

    @Id
    public long id;

    public String equipment;

    public String description;

    public String username;

}
