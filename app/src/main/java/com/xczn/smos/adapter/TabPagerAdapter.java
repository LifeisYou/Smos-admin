package com.xczn.smos.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xczn.smos.entity.FragmentInfo;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/4.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> fragmentInfos;

    public TabPagerAdapter(FragmentManager fm, List<FragmentInfo> fragments) {
        super(fm);
        fragmentInfos = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentInfos.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentInfos.get(position).getFragment();

    }

    @Override
    public int getCount() {
        return fragmentInfos.size();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        //Do NOTHING;
    }
}
