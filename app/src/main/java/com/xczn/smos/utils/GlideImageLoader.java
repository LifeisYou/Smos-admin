package com.xczn.smos.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @Author zhangxiao
 * @Date 2018/4/19 0019
 * @Comment
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView){
        Glide.with(context).load(path).into(imageView);
    }


}
