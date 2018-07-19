package com.xczn.smos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hss01248.notifyutil.NotifyUtil;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.utils.BoxStoreUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //传入context，进行初始化
        SharedPreferencesUtils.init(this);
        BoxStoreUtils.getInstance().setContext(this);
        NotifyUtil.init(getApplicationContext());

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
