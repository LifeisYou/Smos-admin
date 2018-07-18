package com.xczn.smos.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 * ViewPager中Fragment懒加载父类
 */


public class BaseViewPagerFragment extends SupportFragment {

    //控件是否已经初始化
    private boolean isCreateView = false;
    //是否已将加载过数据
    private boolean isLoadData = false;

    private SwipeRefreshLayout srlTask;

    private RecyclerView rvTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_all, null, false);

        initViews(view);
        isCreateView = true;
        return view;
    }

    private void initViews(View view) {
        //初始化控件
        srlTask = view.findViewById(R.id.refresh_layout);
        rvTask = view.findViewById(R.id.recy);
    }


    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        //如果没有加载过就加载，否则就不再加载了
        if(!isLoadData){
            //加载数据操作
            isLoadData=true;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint())
            lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
