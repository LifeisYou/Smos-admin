package com.xczn.smos.ui.fragment.preview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;
import com.xczn.smos.adapter.PhotoPreviewAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.ui.view.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class PhotoViewFragment extends BaseBackFragment {

    private PhotoViewPager vpPhotoView;
    private PhotoPreviewAdapter adapter;
    private List<String> photoUrls;
    private int currentPosition;
    private boolean flag;

    public static PhotoViewFragment newInstance(int position, ArrayList<String> urls, boolean flag) {
        PhotoViewFragment fragment = new PhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putStringArrayList("url", urls);
        bundle.putBoolean("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            currentPosition = getArguments().getInt("position");
            photoUrls = getArguments().getStringArrayList("url");
            flag = getArguments().getBoolean("flag");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(currentPosition+1 + "/" + photoUrls.size());
        initToolbarNav(toolbar);

        vpPhotoView = view.findViewById(R.id.vp_photo_view);
        adapter = new PhotoPreviewAdapter(photoUrls, _mActivity, flag);
        vpPhotoView.setAdapter(adapter);
        vpPhotoView.setCurrentItem(currentPosition, false);
        vpPhotoView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                toolbar.setTitle(currentPosition+1 + "/" + photoUrls.size());
            }
        });
    }
}
