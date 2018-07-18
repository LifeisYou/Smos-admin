package com.xczn.smos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.entity.Task;
import com.xczn.smos.entity.TaskList;
import com.xczn.smos.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/8.
 */
public class AdminTaskAdapter extends RecyclerView.Adapter<AdminTaskAdapter.MyViewHolder> {
    private List<TaskList> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public AdminTaskAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<TaskList> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public AdminTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_admin_task, parent, false);
        final AdminTaskAdapter.MyViewHolder holder = new AdminTaskAdapter.MyViewHolder(view);
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
    public void onBindViewHolder(AdminTaskAdapter.MyViewHolder holder, int position) {
        TaskList item = mItems.get(position);
        holder.tvTaskId.setText(item.getTaskId());
        holder.tvTaskUser.setText(item.getReceiver());
        holder.tvTaskMessage.setText(item.getMessage());
        holder.tvTaskEndLine.setText(item.getTimeDeadline());
        String status = null;
        int color = 0;
        switch (item.getStatus()){
            case 0:
                status = "未接受";
                color = Color.parseColor("#FFA000");
                break;
            case 1:
                status = "已接受";
                color = Color.GREEN;
                break;
            case 2:
                status = "执行中";
                color = Color.GREEN;
                break;
            case 4:
                status = "已拒绝";
                color = Color.RED;
                break;
            case 3:
                status = "已完成";
                color = Color.BLUE;
                break;
            default:
                break;
        }
        holder.tvTaskStatus.setTextColor(color);
        holder.tvTaskStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public TaskList getItem(int position) {
        return mItems.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTaskId, tvTaskStatus,tvTaskUser,tvTaskMessage,tvTaskEndLine;

        MyViewHolder(View itemView) {
            super(itemView);
            tvTaskId = itemView.findViewById(R.id.task_id);
            tvTaskStatus = itemView.findViewById(R.id.task_status);
            tvTaskUser = itemView.findViewById(R.id.task_user);
            tvTaskMessage = itemView.findViewById(R.id.task_message);
            tvTaskEndLine = itemView.findViewById(R.id.task_endLine);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
