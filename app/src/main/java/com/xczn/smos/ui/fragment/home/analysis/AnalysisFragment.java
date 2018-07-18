package com.xczn.smos.ui.fragment.home.analysis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.xczn.smos.R;
import com.xczn.smos.adapter.EquipmentSpinnerAdapter;
import com.xczn.smos.adapter.FactorySpinnerAdapter;
import com.xczn.smos.adapter.IntervalSpinnerAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.request.EquipmentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class AnalysisFragment extends BaseBackFragment implements View.OnClickListener{

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("数据报表");
        initToolbarNav(mToolbar);

        Button btnChartDay = view.findViewById(R.id.btn_chart_day);
        btnChartDay.setOnClickListener(this);
        Button btnChartMonth = view.findViewById(R.id.btn_chart_month);
        btnChartMonth.setOnClickListener(this);
        Button btnChartYear = view.findViewById(R.id.btn_chart_year);
        btnChartYear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_chart_day:
                start(ChartDayFragment.newInstance());
                break;
            case R.id.btn_chart_month:
                start(ChartMonthFragment.newInstance());
                break;
            case R.id.btn_chart_year:
                start(ChartYearFragment.newInstance());
                break;
            default:
                break;
        }
    }
}
