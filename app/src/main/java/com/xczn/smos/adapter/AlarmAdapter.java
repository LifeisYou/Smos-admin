package com.xczn.smos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.dao.Alarm;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.listener.OnPublishClickListener;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ContactsViewHolder> {

    private List<Alarm> alarmList;
    private int layoutId;
    private OnItemClickListener mClickListener;
    private OnPublishClickListener mPublishClickListener;
    private OnPublishClickListener mIgnoreClickListener;

    public AlarmAdapter(List<Alarm> alarmList, int layoutId) {
        this.alarmList = alarmList;
        this.layoutId = layoutId;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        final ContactsViewHolder holder = new ContactsViewHolder(view);
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
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        final int pos = position;
        Alarm alarm = alarmList.get(position);
        holder.tvAlarmMessage.setText(alarm.message);
        holder.tvAlarmEquipment.setText(alarm.equipment);
        holder.tvAlarmLevel.setText(alarm.level);
        holder.tvAlarmTime.setText(alarm.time);
        holder.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPublishClickListener != null){
                    mPublishClickListener.onItemClick(pos);
                }
            }
        });
        holder.btnUnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIgnoreClickListener != null){
                    mIgnoreClickListener.onItemClick(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlarmMessage, tvAlarmEquipment, tvAlarmLevel, tvAlarmTime;
        Button btnPublish, btnUnPublish;


        ContactsViewHolder(View itemView) {
            super(itemView);
            tvAlarmMessage = itemView.findViewById(R.id.tv_alarm_message);
            tvAlarmEquipment = itemView.findViewById(R.id.tv_alarm_equipment);
            tvAlarmLevel = itemView.findViewById(R.id.tv_alarm_level);
            tvAlarmTime = itemView.findViewById(R.id.tv_alarm_time);
            btnPublish = itemView.findViewById(R.id.btn_publish_task);
            btnUnPublish = itemView.findViewById(R.id.btn_un_publish_task);
        }
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnPublishClickListener(OnPublishClickListener publishClickListener) {
        this.mPublishClickListener = publishClickListener;
    }
    public void setOnIgnoreClickListener(OnPublishClickListener ignoreClickListener) {
        this.mIgnoreClickListener = ignoreClickListener;
    }
}
