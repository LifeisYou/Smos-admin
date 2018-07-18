package com.xczn.smos.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;
import com.xczn.smos.SvgActivity;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class TestFragment extends SupportFragment {

    private static final String TAG = TestFragment.class.getSimpleName();
    private static TestFragment fragment;

    public static TestFragment newInstance() {
        fragment = new TestFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        //_mActivity.startActivity(new Intent(_mActivity, SvgActivity.class));
        _mActivity.startActivityFromFragment(fragment,new Intent(_mActivity, SvgActivity.class),22);
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        if (requestCode == 22 && resultCode == RESULT_OK) {
            _mActivity.onBackPressed();
        }
    }
}
