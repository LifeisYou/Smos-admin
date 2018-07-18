package com.xczn.smos.ui.fragment.home.taks_user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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

public class TaskUserFragment extends BaseBackFragment{

    TabLayout tl_adminTask = null;
    ViewPager vp_adminTask = null;

    public static TaskUserFragment newInstance() {

        Bundle args = new Bundle();
        TaskUserFragment fragment = new TaskUserFragment();
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

        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), FragmentInfo.getUserTaskTab());
        vp_adminTask.setOffscreenPageLimit(adapter.getCount());
        vp_adminTask.setAdapter(adapter);
        tl_adminTask.setupWithViewPager(vp_adminTask);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

}
