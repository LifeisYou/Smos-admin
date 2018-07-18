package com.xczn.smos.ui.fragment.monitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;

/**
 * Created by zhangxiao
 * Date on 2018/6/6.
 */
public class Equipment2SearchFragment extends BaseBackFragment {

    public static Equipment2SearchFragment newInstance() {
        return new Equipment2SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }
}
