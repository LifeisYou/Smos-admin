package com.xczn.smos.request;

import com.xczn.smos.entity.UserList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */

public interface AccountService {

    @GET("login")
    Observable<UserList> login(@Query("username") String username, @Query("password") String password);

    @GET("register")
    Observable<UserList> register(@Query("username") String username, @Query("password") String password,
                                   @Query("name") String name, @Query("code") String code, @Query("time") String time);

}
