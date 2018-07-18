package com.xczn.smos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.entity.Equipment1;
import com.xczn.smos.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页HomeFragment  Adapter
 * Created by YoKeyword on 16/2/1.
 */
public class EquipSearchResultAdapter extends RecyclerView.Adapter<EquipSearchResultAdapter.MyViewHolder> {
    private List<Equipment1> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public EquipSearchResultAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Equipment1> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_equip_search_result, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Equipment1 item = mItems.get(position);
        holder.tvEquipName.setText(item.getEquipname() + "--" + item.getEquipid());
        holder.tvEquipDes.setText(item.getType());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Equipment1 getItem(int position) {
        return mItems.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEquipName, tvEquipDes;

        MyViewHolder(View itemView) {
            super(itemView);
            tvEquipName = itemView.findViewById(R.id.tv_equip_name);
            tvEquipDes = itemView.findViewById(R.id.tv_equip_des);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
