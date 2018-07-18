package com.xczn.smos;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xczn.smos.entity.TaskFragment;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskAcceptOrRefuseFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskProcessFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskSummaryFragment;
import com.xczn.smos.ui.fragment.home.task.TaskStepViewFragment;
import com.xczn.smos.utils.BoxStoreUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class TaskActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String taskId = getIntent().getStringExtra("TASK_ID");
        int type = getIntent().getIntExtra("TASK_TYPE", 3);

        //if (findFragment(task.getType()) == null) {
        SupportFragment fragment = null;
        switch (type) {
            case 0:
                fragment = TaskAcceptOrRefuseFragment.newInstance(taskId);
                break;
            case 1:
                fragment = TaskProcessFragment.newInstance(taskId);
                break;
            case 2:
                fragment = TaskSummaryFragment.newInstance(taskId);
                break;
            case 3:
            case 4:
                fragment = TaskStepViewFragment.newInstance(taskId);
                break;
        }
        loadRootFragment(R.id.fl_container, fragment);
        //}
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
}
