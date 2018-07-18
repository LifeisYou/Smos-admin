package com.xczn.smos.ui.fragment.home.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xczn.smos.R;
import com.xczn.smos.TaskActivity;
import com.xczn.smos.adapter.AdminTaskAdapter;
import com.xczn.smos.entity.TaskList;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.ui.view.EmptyLayout;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class TaskAllFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    private static final String TAG = TaskAllFragment.class.getName();
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private EmptyLayout mEmptyLayout;
    private AdminTaskAdapter mAdapter;
    private List<TaskList> tasks = new ArrayList<>();
    private View view;

    public static TaskAllFragment newInstance() {
        return new TaskAllFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_all, container, false);
        Log.d(TAG, "onCreateView: " + "加载");
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
                                   tasks = taskLists;
                                   Log.d(TAG, "accept: 数组大小" + taskLists.size() + "*" + tasks.size());
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
                                           startActivity(intent);
                                       }
                                   });
                                   mAdapter.setDatas(tasks);
                                   mRefreshLayout.setRefreshing(false);
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
                                mRefreshLayout.setRefreshing(false);
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
        }, 500);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: TaskAllFragment is Deatroyed");
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
