package com.xczn.smos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xczn.smos.R;
import com.xczn.smos.entity.MenuItem;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */
public class MenuAdapter extends BaseAdapter{

    private Context context;
    private List<MenuItem> menuItemList;

    public MenuAdapter(Context context,List<MenuItem> menuItemList){
        this.context = context;
        this.menuItemList = menuItemList;
    }

    @Override
    public int getCount(){
        return menuItemList.size();
    }

    @Override
    public Object getItem(int position){
        return menuItemList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        MenuAdapter.ViewHolder holder = null;
        if (holder == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_image,parent);
            convertView = View.inflate(context , R.layout.item_menu,null);
            holder = new MenuAdapter.ViewHolder();
            holder.menuImage = convertView.findViewById(R.id.menu_image);
            holder.menuText = convertView.findViewById(R.id.menu_text);
            convertView.setTag(holder);
        } else {
            holder = (MenuAdapter.ViewHolder) convertView.getTag();
        }
        MenuItem menuItem = menuItemList.get(position);
        Glide.with(context).load(menuItem.getIcon()).into(holder.menuImage);
        holder.menuText.setText(menuItem.getMenu());
        //ImagePicker.getInstance().getImageLoader().displayImage((Activity) context , imageItem.path, holder.imageView, imageItem.width, imageItem.height);
        return convertView;

    }

    public class ViewHolder{
        ImageView menuImage;
        TextView menuText;
    }
}
