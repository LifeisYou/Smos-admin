package com.xczn.smos.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xczn.smos.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 基础返回的Fragment
 */

public class BaseBackFragment extends SupportFragment {
    private static final String TAG = "Fragmentation";

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_back_svg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
