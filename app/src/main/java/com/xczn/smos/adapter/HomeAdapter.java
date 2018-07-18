package com.xczn.smos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.entity.Task;
import com.xczn.smos.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页HomeFragment  Adapter
 * Created by YoKeyword on 16/2/1.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Task> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public HomeAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Task> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home, parent, false);
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
        Task item = mItems.get(position);
        holder.tvTitle.setText(item.getTask().getEquipment());
        holder.tvContent.setText(item.getTask().getMessage());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Task getItem(int position) {
        return mItems.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent;

        MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
