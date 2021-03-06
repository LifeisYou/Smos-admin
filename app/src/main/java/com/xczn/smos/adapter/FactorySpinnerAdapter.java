package com.xczn.smos.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.utils.DensityUtil;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/4.
 */
public class FactorySpinnerAdapter implements SpinnerAdapter {

    private List<Equipment2Tree> factoryList;
    private Context mContext;

    public FactorySpinnerAdapter(List<Equipment2Tree> factoryList, Context mContext) {
        this.factoryList = factoryList;
        this.mContext = mContext;
    }


    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(mContext);
        //textView.setHeight(DensityUtil.px2dip(mContext, 48));
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        //textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setText(factoryList.get(i).getFactory_name());
        return textView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return factoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return factoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(mContext);
        //textView.setHeight(DensityUtil.px2dip(mContext, 48));
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        //textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setText(factoryList.get(i).getFactory_name());
        return textView;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
