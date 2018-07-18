package com.xczn.smos.ui.fragment.home.alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.xczn.smos.R;
import com.xczn.smos.adapter.AlarmAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.dao.Alarm;
import com.xczn.smos.dao.Alarm_;
import com.xczn.smos.listener.OnPublishClickListener;
import com.xczn.smos.utils.BoxStoreUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class AlarmFragment extends BaseBackFragment{

    private static final String TAG = AlarmFragment.class.getName();
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList = new ArrayList<>();
    private Box<Alarm> alarmBox;


    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBox = BoxStoreUtils.getInstance().getBoxStore().boxFor(Alarm.class);
        //boxStore = MyObjectBox.builder().androidContext(_mActivity).build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        String userName = SharedPreferencesUtils.getInstance().getLoginUsername();
        alarmList = alarmBox.query().equal(Alarm_.username, userName).equal(Alarm_.status, "undo").orderDesc(Alarm_.id).build().find();
        Log.d(TAG, "initData: " +alarmList.size());
//        EventBusActivityScope.getDefault(_mActivity).post( new AlarmPostEvent(alarmList.size()));
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("报警");
        initToolbarNav(mToolbar);

        RecyclerView alarmRv = view.findViewById(R.id.rv_alarm);
        alarmRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        System.out.println(alarmList.size());
        alarmAdapter = new AlarmAdapter(alarmList, R.layout.item_alarm_unfinish);
        alarmRv.setAdapter(alarmAdapter);
        alarmAdapter.setOnPublishClickListener(new OnPublishClickListener() {
            @Override
            public void onItemClick(int position) {
                start(TaskPublishFragment.newInstance(
                        alarmList.get(position).equipment, alarmList.get(position).message, alarmList.get(position).alarmId));
            }
        });
        alarmAdapter.setOnIgnoreClickListener(new OnPublishClickListener() {
            @Override
            public void onItemClick(final int position) {
                new TDialog.Builder(getFragmentManager())
                        .setLayoutRes(R.layout.dialog_alarm_ignore)    //设置弹窗展示的xml布局
                        .setScreenWidthAspect(_mActivity, 0.8f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                        .setGravity(Gravity.CENTER_VERTICAL)     //设置弹窗展示位置
                        .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                        .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                        .setCancelable(true)    //弹窗是否可以取消
                        .addOnClickListener(R.id.tv_alarm_yes, R.id.tv_alarm_no)   //添加进行点击控件的id
                        .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.tv_alarm_yes:
                                        Alarm alarm = alarmList.get(position);
                                        alarm.setStatus("done");
                                        alarmBox.put(alarm);
                                        alarmList.remove(alarmList.get(position));
//                                        alarmList = alarmBox.query().equal(Alarm_.username,"123").build().find();
                                        Log.d(TAG, "initData: " +alarmList.size());
                                        alarmAdapter.notifyDataSetChanged();
                                        tDialog.dismiss();
                                        break;
                                    case R.id.tv_alarm_no:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .create()   //创建TDialog
                        .show();    //展示            }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
