package com.xczn.smos.ui.fragment.home.taks_user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Task;
import com.xczn.smos.event.TaskUpdateEvent;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.TimeUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 */
public class TaskAcceptOrRefuseFragment extends BaseBackFragment {

    public static final String TAG = TaskAcceptOrRefuseFragment.class.getSimpleName();
    private static final String TASK_ID = "task_id";
    public static final int TASK_ACCEPT_STATUS = 1;
    public static final int TASK_REFUSE_STATUS = 4;

    private Toolbar mToolbar;
    private TextView tvMessage, tvEquipment, tvPublisher, tvTimeDeadline;
    private EditText etMsgReceive;
    private RadioGroup rgAcceptOrRefuseTask;

    private String taskId;
    private Task mTask;
    private TaskService taskService;

    public static TaskAcceptOrRefuseFragment newInstance(String taskId) {
        TaskAcceptOrRefuseFragment fragment = new TaskAcceptOrRefuseFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TASK_ID, taskId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            taskId = bundle.getString(TASK_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_accept_or_refuse, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Observer<Task> observer= new Observer<Task>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(Task task) {
                mTask = task;
                mToolbar.setTitle(mTask.getTask().getTaskId());
                tvMessage.setText(mTask.getTask().getMessage());
                tvEquipment.setText(mTask.getTask().getEquipment());
                tvPublisher.setText(mTask.getTask().getPublisher());
                tvTimeDeadline.setText(mTask.getTask().getTimeDeadline());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());

                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        };

        taskService = HttpMethods.getRetrofit().create(TaskService.class);
        taskService.getTask(taskId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        initToolbarNav(mToolbar);

        tvMessage = view.findViewById(R.id.tv_task_message);
        tvEquipment = view.findViewById(R.id.tv_task_equipment);
        tvPublisher = view.findViewById(R.id.tv_task_publisher);
        tvTimeDeadline = view.findViewById(R.id.tv_task_time_deadline);
        etMsgReceive = view.findViewById(R.id.et_task_msg_receive);
        rgAcceptOrRefuseTask = view.findViewById(R.id.rg_accept_or_refuse_task);

        Button btnAcceptOrRefuse = view.findViewById(R.id.btn_accept_or_refuse_task);

        btnAcceptOrRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRbId = rgAcceptOrRefuseTask.getCheckedRadioButtonId();
                if (R.id.rb_accept_task == checkedRbId){
                    acceptTask();
                } else {
                    refuseTask();
                }
            }
        });
    }

    private void acceptTask(){
        Observer<String> observer= new Observer<String>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(String str) {
                if ("true".equals(str)){
                    Toast.makeText(_mActivity,"任务接受成功", Toast.LENGTH_SHORT).show();
                    _mActivity.onBackPressed();
                    EventBusActivityScope.getDefault(getActivity()).post(new TaskUpdateEvent(0));
                } else {
                    Toast.makeText(_mActivity,"任务接受失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());
                d.dispose();
            }
            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        };
        taskService.acceptTask(mTask.getTask().getTaskId(), etMsgReceive.getText().toString(),
                TimeUtils.getCurrentTime(), TASK_ACCEPT_STATUS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void refuseTask(){
        Observer<String> observer= new Observer<String>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(String str) {
                if ("true".equals(str)){
                    Toast.makeText(_mActivity,"任务拒绝成功", Toast.LENGTH_SHORT).show();
                    _mActivity.onBackPressed();
                    EventBusActivityScope.getDefault(_mActivity).post(new TaskUpdateEvent(0));
                } else {
                    Toast.makeText(_mActivity,"任务拒绝成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());
                d.dispose();
            }
            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        };
        taskService.refuseTask(mTask.getTask().getTaskId(), etMsgReceive.getText().toString(),
                TimeUtils.getCurrentTime(), TASK_REFUSE_STATUS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
