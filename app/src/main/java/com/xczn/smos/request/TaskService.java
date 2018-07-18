package com.xczn.smos.request;

import com.xczn.smos.entity.Task;
import com.xczn.smos.entity.TaskList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author zhangxiao
 * @Date 2018/3/5 0005
 * @Comment
 */

public interface TaskService {
    /**
     * 任务接口
     *@return List<>
     */
    @GET("task")
    Observable<Task> getTask(@Query("id") String taskId);

    @GET("tasklist")
    Observable<List<TaskList>> getTaskList(@Query("username") String username, @Query("type") String type);

    @GET("newtask")
    Observable<String> publishTask(@Query("taskid") String taskId, @Query("type") String type, @Query("publisher")String publisher,
                                   @Query("receiver")String receiver, @Query("equipment")String equipment,@Query("message")String message,
                                   @Query("status") int status,
                                   @Query("timedeadline")String timeDeadline,@Query("timepublish")String timePublish,
                                   @Query("alarmid") String alarmId);

    @GET("edittask")
    Observable<String> republishTask(@Query("taskid") String taskId, @Query("type") String type, @Query("publisher")String publisher,
                                     @Query("receiver")String receiver, @Query("equipment")String equipment,@Query("message")String message,
                                     @Query("status") int status,
                                     @Query("timedeadline")String timeDeadline,@Query("timepublish")String timePublish,
                                     @Query("alarmid") String alarmId);

    @GET("accepttask")
    Observable<String> acceptTask(@Query("taskid") String taskId, @Query("msgreceive") String msgReceive,
                                  @Query("timereceive") String timeReceive, @Query("status") int status);

    @GET("refusetask")
    Observable<String> refuseTask(@Query("taskid") String taskId, @Query("msgreceive") String msgReceive,
                                  @Query("timereceive") String timeReceive, @Query("status") int status);

    @GET("processtask")
    Observable<String> processTask(@Query("taskid") String taskId, @Query("msgprocess") String msgProcess,
                                   @Query("timeprocess") String timeProcess, @Query("status") int status);

    @GET("summarytask")
    Observable<String> summaryTask(@Query("taskid") String taskId, @Query("msgsummary") String msgSummary,
                                   @Query("timesummary") String timeSummary, @Query("status") int status);
}