package com.xczn.smos.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;

/**
 * Created by zhangxiao
 * Date on 2018/5/21.
 */
public class SoftMessageFragment extends BaseBackFragment {

    public static SoftMessageFragment newInstance() {
        return new SoftMessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mArticle = getArguments().getParcelable(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soft_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("软件信息");
        initToolbarNav(toolbar);
    }


    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}