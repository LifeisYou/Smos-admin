package com.xczn.smos.base;

import android.widget.Toast;

import com.xczn.smos.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 基础的MainFragment
 */
public abstract class BaseMainFragment extends IsLoginFragment {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.cancel, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
