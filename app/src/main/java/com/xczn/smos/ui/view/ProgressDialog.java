package com.xczn.smos.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xczn.smos.R;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 自定义进度条ProgressBar
 */

public class ProgressDialog extends DialogFragment {

    TextView mProgressTv;   // 显示进度的数字
    ImageView mLoadingIv;   // Loading图标

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View mRootView = inflater.inflate(R.layout.progress_dialog, container, false);
        mProgressTv = mRootView.findViewById(R.id.tv_progress_num);
        mLoadingIv = mRootView.findViewById(R.id.iv_progress);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置Loading旋转动画
        RotateAnimation rotateAnimation;
        rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(400L);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        mLoadingIv.startAnimation(rotateAnimation);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = 0.5f;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;

        window.setAttributes(params);
    }

    //IllegalStateException : Can not perform this action after onSaveInstanceSate
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
        }
    }

    /**
     * 初始化ProgressDialog实例
     * @return
     */
    public static ProgressDialog create() {
        ProgressDialog dialog = new ProgressDialog();
        return dialog;
    }

    /**
     * 显示
     * @param manger
     * @return
     */
    public ProgressDialog show(FragmentManager manger) {
        show(manger, this.getTag());
        return this;
    }

    /**
     * 设置当前的进度
     * @param progress
     */
    public void setProgress(int progress) {
        mProgressTv.setText(String.valueOf(progress));
    }
}

