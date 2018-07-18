package com.xczn.smos.utils;

import android.content.Context;

import com.xczn.smos.dao.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by zhangxiao
 * Date on 2018/5/2.
 */
public class BoxStoreUtils {

    private static class BoxStoreHolder {
        public static BoxStoreUtils instance = new BoxStoreUtils();
    }
    public static BoxStoreUtils getInstance(){
        return BoxStoreHolder.instance;
    }

    private Context mContext;
    private BoxStore boxStore;

    public void setContext(Context context){
        this.mContext = context;
    }

    public BoxStore getBoxStore(){
        if (boxStore == null){
            boxStore = MyObjectBox.builder().androidContext(mContext).build();
        }
        return boxStore;
    }

    public void closeBoxStore(){
        if (boxStore != null){
            boxStore.close();
        }
    }
}
