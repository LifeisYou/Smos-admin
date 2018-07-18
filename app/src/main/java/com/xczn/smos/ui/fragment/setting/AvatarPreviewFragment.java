package com.xczn.smos.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.utils.GlideUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class AvatarPreviewFragment extends BaseBackFragment {

    private String mUrl;

    public static AvatarPreviewFragment newInstance(String url) {
        AvatarPreviewFragment fragment = new AvatarPreviewFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString("url");
            mUrl = SharedPreferencesUtils.getInstance().getBaseUrl() + "avatars?id=" + mUrl;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avatar_preview, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("头像");
        initToolbarNav(mToolbar);

        PhotoView pvAvatar = view.findViewById(R.id.pv_avatar);
        GlideUtils.loadImageNoCache(_mActivity, mUrl, pvAvatar);
    }
}
