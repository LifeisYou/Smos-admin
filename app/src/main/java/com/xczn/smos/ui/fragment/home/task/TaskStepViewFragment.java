package com.xczn.smos.ui.fragment.home.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.adapter.AdminTaskAdapter;
import com.xczn.smos.adapter.StepAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Task;
import com.xczn.smos.entity.TaskList;
import com.xczn.smos.event.AlarmPostEvent;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.ui.fragment.home.alarm.TaskPublishFragment;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.TaskUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/5/8.
 */
public class TaskStepViewFragment extends BaseBackFragment {

    public static final String TAG = TaskDetailFragment.class.getSimpleName();
    private static final int REQ_MODIFY_FRAGMENT = 100;

    private static final String TASK_ID = "task_id";
    static final String KEY_RESULT_TITLE = "title";

    private TextView tvTaskStepId,tvTaskStepEndLine,tvTaskMore;
    private RecyclerView rvTaskStep;
    private Button btnTaskRepublish;

    private StepAdapter stepAdapter;
    private String taskId;
    private Task mTask;

    public static TaskStepViewFragment newInstance(String taskId) {
        TaskStepViewFragment fragment = new TaskStepViewFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_step_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("执行情况");
        initToolbarNav(mToolbar);

        tvTaskStepId = view.findViewById(R.id.stepView_taskId);
        tvTaskStepEndLine = view.findViewById(R.id.stepView_endLine);
        tvTaskMore = view.findViewById(R.id.stepView_more);
        btnTaskRepublish = view.findViewById(R.id.btn_task_republish);
        rvTaskStep = view.findViewById(R.id.rv_task_step);

        tvTaskMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(TaskDetailFragment.newInstance(mTask));
            }
        });
        btnTaskRepublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(TaskRepublishFragment.newInstance(mTask.getTask().getTaskId(), mTask.getTask().getEquipment(),
                        mTask.getTask().getMessage(), mTask.getTask().getAlarmId()));
            }
        });
    }

    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        initDelayView();
    }

    private void initDelayView() {

        Observer<Task> observer= new Observer<Task>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(Task task) {
                mTask = task;
                if (mTask.getTask().getStatus() == 4){
                    btnTaskRepublish.setVisibility(View.VISIBLE);
                }
                tvTaskStepId.setText(mTask.getTask().getTaskId());
                tvTaskStepEndLine.setText(mTask.getTask().getTimeDeadline());
                stepAdapter = new StepAdapter(_mActivity);
                LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                rvTaskStep.setLayoutManager(manager);
                rvTaskStep.setAdapter(stepAdapter);
                System.out.println("Size:"+TaskUtils.Task2TaskStep(mTask).size());
                stepAdapter.setDatas(TaskUtils.Task2TaskStep(mTask));
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

        TaskService taskService = HttpMethods.getRetrofit().create(TaskService.class);
        taskService.getTask(taskId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

