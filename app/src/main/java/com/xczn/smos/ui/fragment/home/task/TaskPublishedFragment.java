package com.xczn.smos.ui.fragment.home.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xczn.smos.R;
import com.xczn.smos.TaskActivity;
import com.xczn.smos.adapter.AdminTaskAdapter;
import com.xczn.smos.base.ViewPagerFragment;
import com.xczn.smos.entity.Task;
import com.xczn.smos.entity.TaskList;
import com.xczn.smos.event.AlarmPostEvent;
import com.xczn.smos.event.TabSelectedEvent;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.ui.view.EmptyLayout;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class TaskPublishedFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private EmptyLayout mEmptyLayout;
    private AdminTaskAdapter mAdapter;
    private List<TaskList> tasks = new ArrayList<>();

    public static TaskPublishedFragment newInstance() {
        return new TaskPublishedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_task_all, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = view.findViewById(R.id.recy);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);

        mEmptyLayout = view.findViewById(R.id.task_emptyLayout);
        mEmptyLayout.setLoadingMessage("正在加载中...");
        //mEmptyLayout.showLoading(R.drawable.ic_loading, "正在加载中。。。");
        mEmptyLayout.showLoading();

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);
        getDataTest();
    }

    @SuppressLint("CheckResult")
    public void getDataTest() {
        HttpMethods.getRetrofit().create(TaskService.class)
                .getTaskList(SharedPreferencesUtils.getInstance().getLoginUsername(),"admin")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskList>>() {
                               @Override
                               public void accept(List<TaskList> taskLists) throws Exception {
                                   tasks.clear();
                                   for (TaskList task : taskLists){
                                       if (task.getStatus() == 0) {
                                           tasks.add(task);
                                       }
                                   }
                                   mAdapter = new AdminTaskAdapter(_mActivity);
                                   LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                                   mRecy.setLayoutManager(manager);
                                   mRecy.setAdapter(mAdapter);
                                   mAdapter.setOnItemClickListener(new OnItemClickListener() {
                                       @Override
                                       public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                                           Intent intent = new Intent(_mActivity, TaskActivity.class);
                                           intent.putExtra("TASK_ID", mAdapter.getItem(position).getTaskId());
                                           intent.putExtra("TASK_TYPE",3);
                                           startActivity(intent);                                       }
                                   });
                                   mAdapter.setDatas(tasks);
                                   if (tasks.isEmpty()) {
                                       mEmptyLayout.showEmpty();
                                   } else {
                                       mEmptyLayout.hide();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                               // Log.d(TAG, "Error");
                            }
                        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataTest();
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
