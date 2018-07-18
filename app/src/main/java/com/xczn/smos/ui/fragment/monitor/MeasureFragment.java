package com.xczn.smos.ui.fragment.monitor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.config.Chart;
import com.xczn.smos.entity.ChartBean;
import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.request.ChartService;
import com.xczn.smos.utils.ChartUtils;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/5/8.
 */
public class MeasureFragment extends BaseBackFragment{

    public static final String EQUIP = "equipment2bean";

    private LineChart LI_LineChart, LU_LineChart, K_LineChart;
    private TextView tvEquipmentName,tvEquipmentDes,tvEquipmentFactory,tvEquipmentInterval,tvEquipmentStatus;
    private Equipment2Tree.IntervalsBean.EquipmentsBean mEquipmentsBean;

    public static MeasureFragment newInstance(Equipment2Tree.IntervalsBean.EquipmentsBean equipmentsBean) {

        MeasureFragment fragment = new MeasureFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EQUIP, equipmentsBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mEquipmentsBean = (Equipment2Tree.IntervalsBean.EquipmentsBean)bundle.getSerializable(EQUIP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        initView(view);
        initData();
        initCharts();
        return view;
    }

    private void initData() {
        tvEquipmentName.setText("名称："+mEquipmentsBean.getEquipment_name());
        tvEquipmentDes.setText("描述："+mEquipmentsBean.getEquipment_type());
        tvEquipmentInterval.setText("间隔："+mEquipmentsBean.getInterval_name());
        tvEquipmentFactory.setText("厂站："+mEquipmentsBean.getFactory_name());
        if (mEquipmentsBean.getCommunication_status() == 1){
            tvEquipmentStatus.setText("状态：运行中");
        } else {
            tvEquipmentStatus.setText("状态：未运行");
        }

    }


    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(mEquipmentsBean.getEquipment_name());
        initToolbarNav(mToolbar);

        Button btnMeasureMore = view.findViewById(R.id.btn_measure_more);
        btnMeasureMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String equipId = String.valueOf(mEquipmentsBean.getEquipment_id());
                String equipName = String.valueOf(mEquipmentsBean.getEquipment_name());
                start(MeasureDataFragment.newInstance(equipId, equipName));
            }
        });

        tvEquipmentName = view.findViewById(R.id.equipment2_name);
        tvEquipmentDes = view.findViewById(R.id.equipment2_description);
        tvEquipmentFactory = view.findViewById(R.id.equipment2_factory);
        tvEquipmentInterval = view.findViewById(R.id.equipment2_interval);
        tvEquipmentStatus = view.findViewById(R.id.equipment2_status);

        //线电流
        LI_LineChart = view.findViewById(R.id.LI_LineChart);
        ChartUtils.initDayLineChart(LI_LineChart, "线电流");
        //线电压
        LU_LineChart = view.findViewById(R.id.LU_LineChart);
        ChartUtils.initDayLineChart(LU_LineChart, "线电压");
        //电度
        K_LineChart = view.findViewById(R.id.K_LineChart);
        ChartUtils.initDayLineChart(K_LineChart, "电度");
    }

    @SuppressLint("CheckResult")
    private void initCharts(){
        HttpMethods.getRetrofit().create(ChartService.class)
                .getChartData(Chart.Time_DAY, mEquipmentsBean.getEquipment_id(),Chart.MeaType_IA, TimeUtils.getCurrentDay())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChartBean>() {
                    @Override
                    public void accept(ChartBean chartBean) {
                        //线电流
                        if (chartBean.getLineCurrentAB().size() != 0 && chartBean.getLineCurrentBC().size() != 0 && chartBean.getLineCurrentAC().size() != 0) {

                            LineData LILineData = new LineData(
                                    getLineDataSet(chartBean.getLineCurrentAB(), Color.RED, "Iab")
                                    , getLineDataSet(chartBean.getLineCurrentBC(), Color.GREEN, "Ibc")
                                    , getLineDataSet(chartBean.getLineCurrentAC(), Color.YELLOW, "Ica"));
                            LILineData.setDrawValues(false);
                            LI_LineChart.setData(LILineData);
                            LI_LineChart.invalidate();
                        }
                        //线电压
                        if (chartBean.getLineVoltageAB().size() != 0 && chartBean.getLineVoltageBC().size() != 0 && chartBean.getLineVoltageAC().size() != 0) {
                            LineData LULineData = new LineData(
                                    getLineDataSet(chartBean.getLineVoltageAB(), Color.RED, "Uab")
                                    , getLineDataSet(chartBean.getLineVoltageBC(), Color.GREEN, "Ubc")
                                    , getLineDataSet(chartBean.getLineVoltageAC(), Color.YELLOW, "Uca"));
                            LULineData.setDrawValues(false);
                            LU_LineChart.setData(LULineData);
                            LU_LineChart.invalidate();
                        }
                        //电度
                        if (chartBean.getDegreeP().size() != 0 && chartBean.getDegreeN().size() != 0) {
                            LineData KLineData = new LineData(
                                    getLineDataSet(chartBean.getDegreeP(), Color.RED, "有功")
                                    , getLineDataSet(chartBean.getDegreeN(), Color.GREEN, "无功"));
                            KLineData.setDrawValues(false);
                            K_LineChart.setData(KLineData);
                            K_LineChart.invalidate();
                        }
                    }
                });
    }

    private LineDataSet getLineDataSet(List<ChartBean.ChartData> list, int color, String type){
        List<Entry> mEntries = new ArrayList<>();
        if (list.size() != 0) {
            for (ChartBean.ChartData bean : list) {
                String time = bean.getTime().split(" ")[1];
                mEntries.add(new Entry(Float.parseFloat(time), Float.parseFloat(bean.getValue())));
            }
            Collections.reverse(mEntries);
        }
        LineDataSet mLineDataSet = new LineDataSet(mEntries, type);
        mLineDataSet.setColor(color);
        return mLineDataSet;
    }

}
