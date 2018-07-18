package com.xczn.smos.adapter;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class PhotoPreviewAdapter extends PagerAdapter {


    public static final String TAG = PhotoPreviewAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private FragmentActivity activity;
    private boolean flag;

    public PhotoPreviewAdapter(List<String> imageUrls, FragmentActivity activity, boolean flag) {
        this.imageUrls = imageUrls;
        this.activity = activity;
        this.flag = flag;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        if (flag){
            url = SharedPreferencesUtils.getInstance().getBaseUrl()+"/pictures?id=" + url;
            Glide.with(activity).load(url).into(photoView);
         } else {
            Glide.with(activity).load(Uri.fromFile(new File(url))).into(photoView);
        }

        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
