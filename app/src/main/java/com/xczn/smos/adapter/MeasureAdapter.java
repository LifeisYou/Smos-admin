package com.xczn.smos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xczn.smos.R;
import com.xczn.smos.dao.Alarm;
import com.xczn.smos.entity.MeasureBean;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.listener.OnPublishClickListener;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */
public class MeasureAdapter extends RecyclerView.Adapter<MeasureAdapter.MeasuresViewHolder> {

    private MeasureBean measureBeans;
    private int layoutId;

    public MeasureAdapter(MeasureBean measureBeans, int layoutId) {
        this.measureBeans = measureBeans;
        this.layoutId = layoutId;
    }

    @Override
    public MeasuresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        final MeasuresViewHolder holder = new MeasuresViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MeasuresViewHolder holder, int position) {
        System.out.println(position);
        int num1 = measureBeans.getListYc().size();
        int num2 = measureBeans.getListYc().size() + measureBeans.getListYx().size();
        int num3 = measureBeans.getListYc().size() + measureBeans.getListYx().size() + measureBeans.getListYm().size();
        if (position == 0 ) {
            holder.tvMeasureType.setText("遥测");
            holder.tvMeasureType.setVisibility(View.VISIBLE);
            holder.tvMeasureName.setText(measureBeans.getListYc().get(position).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYc().get(position).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYc().get(position).getTime());
        } else if (position <= num1 - 1) {
            holder.tvMeasureType.setVisibility(View.GONE);
            holder.tvMeasureName.setText(measureBeans.getListYc().get(position).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYc().get(position).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYc().get(position).getTime());
        } else  if (position == num1) {
            holder.tvMeasureType.setText("遥信");
            holder.tvMeasureType.setVisibility(View.VISIBLE);
            holder.tvMeasureName.setText(measureBeans.getListYx().get(position - num1).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYx().get(position - num1).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYx().get(position - num1).getTime());
        } else if (position <= num2 - 1) {
            holder.tvMeasureType.setVisibility(View.GONE);
            holder.tvMeasureName.setText(measureBeans.getListYx().get(position - num1).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYx().get(position - num1).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYx().get(position - num1).getTime());
        } else if (position == num2) {
            holder.tvMeasureType.setText("遥脉");
            holder.tvMeasureType.setVisibility(View.VISIBLE);
            holder.tvMeasureName.setText(measureBeans.getListYm().get(position - num2).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYm().get(position - num2).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYm().get(position - num2).getTime());
        } else if (position <= num3 - 1) {
            holder.tvMeasureType.setVisibility(View.GONE);
            holder.tvMeasureName.setText(measureBeans.getListYm().get(position - num2).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListYm().get(position - num2).getValue());
            holder.tvMeasureTime.setText(measureBeans.getListYm().get(position - num2).getTime());
        } else if (position == num3) {
            holder.tvMeasureType.setText("定值");
            holder.tvMeasureType.setVisibility(View.VISIBLE);
            holder.tvMeasureName.setText(measureBeans.getListSet().get(position - num3).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListSet().get(position - num3).getValue());
        } else {
            holder.tvMeasureType.setVisibility(View.GONE);
            holder.tvMeasureName.setText(measureBeans.getListSet().get(position - num3).getMesname());
            holder.tvMeasureValue.setText(measureBeans.getListSet().get(position - num3).getValue());
        }
    }

    @Override
    public int getItemCount() {
        return measureBeans.getListYc().size()
                + measureBeans.getListYx().size()
                + measureBeans.getListYm().size()
                + measureBeans.getListSet().size();
    }

    class MeasuresViewHolder extends RecyclerView.ViewHolder {
        TextView tvMeasureType, tvMeasureName, tvMeasureValue, tvMeasureTime;

        MeasuresViewHolder(View itemView) {
            super(itemView);
            tvMeasureType = itemView.findViewById(R.id.tv_measure_type);
            tvMeasureName = itemView.findViewById(R.id.tv_measure_name);
            tvMeasureValue = itemView.findViewById(R.id.tv_measure_value);
            tvMeasureTime = itemView.findViewById(R.id.tv_measure_time);
        }
    }
}
