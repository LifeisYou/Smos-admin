package com.xczn.smos.utils;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.Model;
import com.bumptech.glide.request.RequestOptions;
import com.xczn.smos.R;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class GlideUtils {

    public static final RequestOptions optionsNoCache = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.ic_picture_error)
            .placeholder(R.drawable.ic_picture_error)
            .fallback(R.drawable.ic_picture_error);

    private static final RequestOptions optionsNormal = new RequestOptions()
            .error(R.drawable.ic_picture_error)
            .placeholder(R.drawable.ic_picture_error)
            .fallback(R.drawable.ic_picture_error);

    public static void loadImageNoCache(FragmentActivity activity, String url, ImageView imageView){
        Glide.with(activity)
                .load(url)
                .apply(optionsNoCache)
                .into(imageView);
    }

    public static void loadImageNormal(FragmentActivity activity, String url, ImageView imageView){
        Glide.with(activity)
                .load(url)
                .apply(optionsNoCache)
                .into(imageView);
    }
}
