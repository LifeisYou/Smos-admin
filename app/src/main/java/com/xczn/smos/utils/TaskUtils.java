package com.xczn.smos.utils;

import com.xczn.smos.entity.Task;
import com.xczn.smos.entity.TaskStepBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/10.
 */
public class TaskUtils {

    public static List<TaskStepBean> Task2TaskStep(Task mTask){
        List<TaskStepBean> taskStepBeanList = new ArrayList<>();
        Task.TaskBean task =  mTask.getTask();

        if (!task.getTimeSummary().equals("")){
            taskStepBeanList.add(new TaskStepBean(
                    task.getTimeSummary(),task.getPublisher(),task.getReceiver(),task.getMsgSummary(),3));
        }
        if (!task.getTimeProcess().equals("")){
            taskStepBeanList.add(new TaskStepBean(
                    task.getTimeProcess(),task.getPublisher(),task.getReceiver(),task.getMsgProcess(),2));
        }
        if (!task.getTimeReceive().equals("")){
            if (task.getStatus() != 4){
                taskStepBeanList.add(new TaskStepBean(
                        task.getTimeReceive(),task.getPublisher(),task.getReceiver(),task.getMsgReceive(),1));
            } else {
                taskStepBeanList.add(new TaskStepBean(
                        task.getTimeReceive(),task.getPublisher(),task.getReceiver(),task.getMsgReceive(),4));
            }
        }
        taskStepBeanList.add(new TaskStepBean(
                task.getTimePublish(),task.getPublisher(),task.getReceiver(),task.getMessage(),0));


        return taskStepBeanList;
    }
}
