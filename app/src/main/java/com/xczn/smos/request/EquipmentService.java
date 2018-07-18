package com.xczn.smos.request;

import com.xczn.smos.entity.Equipment1;
import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.entity.MeasureBean;
import com.xczn.smos.entity.StationMsgBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangxiao
 * Date on 2018/5/13.
 */
public interface EquipmentService {

    @GET("equipment2tree")
    Observable<List<Equipment2Tree>> getEquipment2Tree();

    @GET("equipment1list")
    Observable<List<Equipment1>> getEquipment1List();

    @GET("station")
    Observable<List<StationMsgBean>> getStationMsg();

    @GET("measures")
    Observable<MeasureBean> getMeasureData(@Query("id") String id, @Query("num") int num);
}
