package com.xczn.smos.ui.fragment.home.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xczn.smos.R;
import com.xczn.smos.adapter.TabPagerAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.FragmentInfo;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment 任务
 */

public class TaskFragment extends BaseBackFragment{

    private static final String TAG = TaskFragment.class.getName();
    private TabLayout tl_adminTask;
    private ViewPager vp_adminTask;
    private TabPagerAdapter adapter;

    public static TaskFragment newInstance() {

        Bundle args = new Bundle();
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initView(View view){

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的任务");
        initToolbarNav(toolbar);

        tl_adminTask = view.findViewById(R.id.tl_admin_task);
        vp_adminTask = view.findViewById(R.id.vp_admin_task);

        adapter = new TabPagerAdapter(getChildFragmentManager(), FragmentInfo.getAdminTaskTab());
        vp_adminTask.setOffscreenPageLimit(adapter.getCount());
        vp_adminTask.setAdapter(adapter);
        tl_adminTask.setTabMode(TabLayout.MODE_FIXED);
        tl_adminTask.setupWithViewPager(vp_adminTask);

        tl_adminTask.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_adminTask.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    public void onDestroyView() {
        super.onDestroyView();
//        if (vp_adminTask != null) {
//            vp_adminTask = null;
//            Log.d(TAG, "onDestroyView: " +"vp_adminTask");
//        }
        //EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

}
