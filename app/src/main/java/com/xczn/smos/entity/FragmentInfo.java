package com.xczn.smos.entity;

import android.support.v4.app.Fragment;

import com.xczn.smos.ui.fragment.home.taks_user.TaskUserAllFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskUserDoingFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskUserFinishedFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskUserPublishedFragment;
import com.xczn.smos.ui.fragment.home.task.TaskAllFragment;
import com.xczn.smos.ui.fragment.home.task.TaskDoingFragment;
import com.xczn.smos.ui.fragment.home.task.TaskFinishedFragment;
import com.xczn.smos.ui.fragment.home.task.TaskPublishedFragment;
import com.xczn.smos.ui.fragment.home.task.TaskRefusedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/5.
 */
public class FragmentInfo {
    private String title;

    private Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public static List<FragmentInfo> getAdminTaskTab(){
        List<FragmentInfo> mFragments = new ArrayList<>(5);
        mFragments.add(new FragmentInfo("全部", TaskAllFragment.newInstance()));
        mFragments.add(new FragmentInfo("已发布", TaskPublishedFragment.newInstance()));
        mFragments.add(new FragmentInfo("执行中", TaskDoingFragment.newInstance()));
        mFragments.add(new FragmentInfo("已完成", TaskFinishedFragment.newInstance()));
        mFragments.add(new FragmentInfo("拒绝", TaskRefusedFragment.newInstance()));
        return mFragments;
    }

    public static List<FragmentInfo> getUserTaskTab(){
        List<FragmentInfo> mFragments = new ArrayList<>(4);
        mFragments.add(new FragmentInfo("全部", TaskUserAllFragment.newInstance()));
        mFragments.add(new FragmentInfo("未接受", TaskUserPublishedFragment.newInstance()));
        mFragments.add(new FragmentInfo("执行中", TaskUserDoingFragment.newInstance()));
        mFragments.add(new FragmentInfo("已完成", TaskUserFinishedFragment.newInstance()));
        return mFragments;
    }
}
