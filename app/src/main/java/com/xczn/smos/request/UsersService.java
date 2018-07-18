package com.xczn.smos.request;

import com.xczn.smos.entity.User;
import com.xczn.smos.entity.UserList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author zhangxiao
 * @Date 2018/4/24 0024
 * @Comment
 */

public interface UsersService {
    /**
     * 任务接口
     *@return List<>
     */
    @GET("userlist")
    Observable<List<UserList>> getUserList();

    @GET("user")
    Observable<User> getUserInfo(@Query("id") String userId);

    @GET("edituser")
    Observable<User> editUserInfo(@Query("id") String userId, @Query("name") String name,
                                  @Query("type") String type, @Query("phone") String phone,
                                  @Query("address") String address, @Query("timecreate") String time);

}
