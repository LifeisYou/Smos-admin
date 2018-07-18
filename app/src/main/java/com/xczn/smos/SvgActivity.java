package com.xczn.smos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xczn.smos.ui.fragment.home.SvgFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class SvgActivity extends SupportActivity {

    private static final String TAG = SvgActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRootFragment(R.id.fl_container, SvgFragment.newInstance());
    }

    @Override
    public void onBackPressedSupport() {
        setResult(RESULT_OK);
        Log.d(TAG, "onBackPressedSupport: ");
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }


}
