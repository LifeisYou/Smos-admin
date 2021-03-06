package com.xczn.smos.request;

import com.xczn.smos.entity.Chart;
import com.xczn.smos.entity.ChartBean;
import com.xczn.smos.entity.UserList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */

public interface ChartService {

    @GET("charts")
    Observable<ChartBean> getChartData(@Query("timetype") int timeType, @Query("equipment2id") int equipment2Id
    , @Query("meatype") int meaType, @Query("time") String time);


}
