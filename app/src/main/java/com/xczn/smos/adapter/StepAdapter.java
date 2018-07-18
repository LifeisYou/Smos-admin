package com.xczn.smos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.entity.TaskStepBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/4/27 0027
 * @Comment
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MyViewHolder> {
    private List<TaskStepBean> mItems = new ArrayList<>();
    private LayoutInflater mInflater;


//    private OnItemClickListener mClickListener;

    public StepAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<TaskStepBean> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_stepview, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (mClickListener != null) {
//                    mClickListener.onItemClick(position, v, holder);
//                }
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskStepBean item = mItems.get(position);
        String type;
        int color = 0;
        switch (item.getStatus()){
            case 0:
                type = "已发布";
                color = Color.parseColor("#FFA000");
                break;
            case 1:
                type = "已接受";
                color = Color.GREEN;
                break;
            case 2:
                type = "执行中";
                color = Color.GREEN;
                break;
            case 4:
                type = "已拒绝";
                color = Color.RED;
                break;
            default:
                type = "已完成";
                color = Color.BLUE;
                break;
        }

        holder.tvDay.setText(item.getDay());
        holder.tvTime.setText(item.getTime());
        holder.tvType.setTextColor(color);
        holder.tvType.setText(type);
        holder.tvMessage.setText(item.getMessage());
        if (position == mItems.size()-1){
            holder.stepLine.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public TaskStepBean getItem(int position) {
        return mItems.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tvMessage,tvDay,tvTime;
        private ImageView ivTask1;
       // private RelativeLayout layoutStepView;
        private View stepLine;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.stepView_type_tv);
            tvMessage = itemView.findViewById(R.id.stepView_message_tv);
            //ivTask1 = itemView.findViewById(R.id.stepView_image1_tv);
            tvDay = itemView.findViewById(R.id.stepView_day_tv);
            tvTime = itemView.findViewById(R.id.stepView_time_tv);
           // layoutStepView =itemView.findViewById(R.id.step_view_layout);
            stepLine = itemView.findViewById(R.id.stepView_line_v);
        }
    }

//    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
}
