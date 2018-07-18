package com.xczn.smos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xczn.smos.R;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/4/25 0025
 * @Comment
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> pictures;

    public ImageAdapter(Context context, List<String> pictures){
        this.context = context;
        this.pictures = pictures;
    }

    @Override
    public int getCount(){
        return pictures.size();
    }

    @Override
    public Object getItem(int position){
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position ,View convertView ,ViewGroup parent){
        ViewHolder holder = null;
        if (holder == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_image,parent);
            convertView = View.inflate(context , R.layout.list_item_image , null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = SharedPreferencesUtils.getInstance().getBaseUrl()+"/pictures?id="+pictures.get(position);
        Glide.with(context).load(url).into(holder.imageView);
        //ImagePicker.getInstance().getImageLoader().displayImage((Activity) context , imageItem.path, holder.imageView, imageItem.width, imageItem.height);
        return convertView;

    }

    public class ViewHolder{
        ImageView imageView;
    }

}
