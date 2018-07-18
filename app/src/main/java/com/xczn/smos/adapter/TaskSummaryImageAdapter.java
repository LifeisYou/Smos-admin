package com.xczn.smos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.xczn.smos.R;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 * 任务图片适配器
 */
public class TaskSummaryImageAdapter extends BaseAdapter {

    private Context context;
    private List<ImageItem> pictures;

    public TaskSummaryImageAdapter(Context context, List<ImageItem> pictures){
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
    public View getView(int position , View convertView , ViewGroup parent){
        TaskSummaryImageAdapter.ViewHolder holder = null;
        if (holder == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_image,parent);
            convertView = View.inflate(context , R.layout.list_item_image , null);
            holder = new TaskSummaryImageAdapter.ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_img);
            holder.imageItem = pictures.get(position);
            convertView.setTag(holder);
        } else {
            holder = (TaskSummaryImageAdapter.ViewHolder) convertView.getTag();
        }
        ImagePicker.getInstance().getImageLoader().displayImage((Activity) context , holder.imageItem.path, holder.imageView, holder.imageItem.width, holder.imageItem.height);
        return convertView;

    }

    public class ViewHolder{
        ImageView imageView;
        ImageItem imageItem;
    }

}
