package com.xczn.smos.ui.fragment.home.analysis;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.xczn.smos.R;
import com.xczn.smos.adapter.EquipmentSpinnerAdapter;
import com.xczn.smos.adapter.FactorySpinnerAdapter;
import com.xczn.smos.adapter.IntervalSpinnerAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.config.Chart;
import com.xczn.smos.entity.ChartBean;
import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.event.DateEvent;
import com.xczn.smos.request.ChartService;
import com.xczn.smos.request.EquipmentService;
import com.xczn.smos.utils.ChartUtils;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.TimeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/6/21.
 * 日报表Fragment
 */
public class ChartYearFragment extends BaseBackFragment {

    public static final String TAG = ChartYearFragment.class.getName();

    private Spinner spinnerFactory;
    private Spinner spinnerInterval;
    private Spinner spinnerEquipment;

    private Button btnSelectDay;

    private LineChart LI_LineChart;
    private LineChart PI_LineChart;
    private LineChart LU_LineChart;
    private LineChart PU_LineChart;
    private LineChart P_LineChart;
    private LineChart K_LineChart;


    private FactorySpinnerAdapter factorySpinnerAdapter;
    private IntervalSpinnerAdapter intervalSpinnerAdapter;
    private EquipmentSpinnerAdapter equipmentSpinnerAdapter;

    private List<Equipment2Tree> systemEquipments = new ArrayList<>();
    private List<Equipment2Tree.IntervalsBean> intervalsList = new ArrayList<>();
    private List<Equipment2Tree.IntervalsBean.EquipmentsBean> equipmentsList = new ArrayList<>();

    private String selectYear = TimeUtils.getCurrentYear();
    private int equipment2Id = 0;

    public static ChartYearFragment newInstance() {
        return new ChartYearFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_day, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("年报表");
        initToolbarNav(mToolbar);

        btnSelectDay = view.findViewById(R.id.btn_select_day);
        btnSelectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                SelectYearFragment fragment = new SelectYearFragment();
                fragment.show(ft, "select_year");
            }
        });

        spinnerFactory = view.findViewById(R.id.factory_spinner);
        spinnerInterval = view.findViewById(R.id.interval_spinner);
        spinnerEquipment = view.findViewById(R.id.equipment_spinner);

        //线电流
        LI_LineChart = view.findViewById(R.id.LI_LineChart);
        ChartUtils.initYearLineChart(LI_LineChart, "线电流");
        //相电流
        PI_LineChart = view.findViewById(R.id.PI_LineChart);
        ChartUtils.initYearLineChart(PI_LineChart, "相电流");

        //线电压
        LU_LineChart = view.findViewById(R.id.LU_LineChart);
        ChartUtils.initYearLineChart(LU_LineChart, "线电压");
        //相电压
        PU_LineChart = view.findViewById(R.id.PU_LineChart);
        ChartUtils.initYearLineChart(PU_LineChart, "相电压");
        //系数
        P_LineChart = view.findViewById(R.id.P_LineChart);
        ChartUtils.initYearLineChart(P_LineChart, "系数");
        //电度
        K_LineChart = view.findViewById(R.id.K_LineChart);
        ChartUtils.initYearLineChart(K_LineChart, "电度");
    }

    private void initData() {

        Observer<List<Equipment2Tree>> observer= new Observer<List<Equipment2Tree>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(List<Equipment2Tree> myEquips) {
                systemEquipments = myEquips;
                Log.d("MAIN", "获取数据完成" + systemEquipments.size());
                factorySpinnerAdapter = new FactorySpinnerAdapter(systemEquipments,_mActivity);
//                factorySpinnerAdapter.notify();
                spinnerFactory.setAdapter(factorySpinnerAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());
                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        };

        EquipmentService service = HttpMethods.getRetrofit().create(EquipmentService.class);
        service.getEquipment2Tree()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer);

        spinnerFactory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intervalsList = systemEquipments.get(i).getIntervals();
                intervalSpinnerAdapter = new IntervalSpinnerAdapter(intervalsList, _mActivity);
                spinnerInterval.setAdapter(intervalSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                equipmentsList = intervalsList.get(i).getEquipments();
                equipmentSpinnerAdapter = new EquipmentSpinnerAdapter(equipmentsList, _mActivity);
                spinnerEquipment.setAdapter(equipmentSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(_mActivity, equipmentsList.get(i).getEquipment_name()+"+"+equipmentsList.get(i).getEquipment_id(), Toast.LENGTH_SHORT).show();
                equipment2Id = equipmentsList.get(i).getEquipment_id();
                initCharts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @SuppressLint("CheckResult")
    private void initCharts(){
        HttpMethods.getRetrofit().create(ChartService.class)
                .getChartData(Chart.Time_YEAR, equipment2Id, Chart.MeaType_IA, selectYear)
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
                                   } else {
                                       LI_LineChart.setData(new LineData());
                                       LI_LineChart.invalidate();
                                   }
                                   //相电流
                                   if (chartBean.getPhaseCurrentA().size() != 0 && chartBean.getPhaseCurrentB().size() != 0 && chartBean.getPhaseCurrentC().size() != 0) {
                                       LineData PILineData = new LineData(
                                               getLineDataSet(chartBean.getPhaseCurrentA(), Color.RED, "Ia")
                                               , getLineDataSet(chartBean.getPhaseCurrentB(), Color.GREEN, "Ib")
                                               , getLineDataSet(chartBean.getPhaseCurrentC(), Color.YELLOW, "Ic"));
                                       PILineData.setDrawValues(false);
                                       PI_LineChart.setData(PILineData);
                                       PI_LineChart.invalidate();
                                   } else {
                                       PI_LineChart.setData(new LineData());
                                       PI_LineChart.invalidate();
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
                                   } else {
                                       LU_LineChart.setData(new LineData());
                                       LU_LineChart.invalidate();
                                   }
                                   //相电压
                                   if (chartBean.getPhaseVoltageA().size() != 0 && chartBean.getPhaseVoltageB().size() != 0 && chartBean.getPhaseVoltageC().size() != 0) {
                                       LineData PULineData = new LineData(
                                               getLineDataSet(chartBean.getPhaseVoltageA(), Color.RED, "Ua")
                                               , getLineDataSet(chartBean.getPhaseVoltageB(), Color.GREEN, "Ub")
                                               , getLineDataSet(chartBean.getPhaseVoltageC(), Color.YELLOW, "Uc"));
                                       PULineData.setDrawValues(false);
                                       PU_LineChart.setData(PULineData);
                                       PU_LineChart.invalidate();
                                   } else {
                                       PU_LineChart.setData(new LineData());
                                       PU_LineChart.invalidate();
                                   }
                                   //系数
                                   if (chartBean.getModulusP().size() != 0 && chartBean.getModulusQ().size() != 0 && chartBean.getModulusCOS().size() != 0) {
                                       LineData PLineData = new LineData(
                                               getLineDataSet(chartBean.getModulusP(), Color.RED, "P")
                                               , getLineDataSet(chartBean.getModulusQ(), Color.GREEN, "Q")
                                               , getLineDataSet(chartBean.getModulusCOS(), Color.YELLOW, "COS"));
                                       PLineData.setDrawValues(false);
                                       P_LineChart.setData(PLineData);
                                       P_LineChart.invalidate();
                                   } else {
                                       P_LineChart.setData(new LineData());
                                       P_LineChart.invalidate();
                                   }
                                   //电度
                                   if (chartBean.getDegreeP().size() != 0 && chartBean.getDegreeN().size() != 0 ){
                                       LineData KLineData = new LineData(
                                               getLineDataSet(chartBean.getDegreeP(), Color.RED, "有功")
                                               , getLineDataSet(chartBean.getDegreeN(), Color.GREEN, "无功"));
                                       KLineData.setDrawValues(false);
                                       K_LineChart.setData(KLineData);
                                       K_LineChart.invalidate();
                                   } else {
                                       K_LineChart.setData(new LineData());
                                       K_LineChart.invalidate();
                                   }
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.d(TAG, "accept: "+ "year 出错");
                            }
                        });
    }

    private LineDataSet getLineDataSet(List<ChartBean.ChartData> list, int color, String type){
        List<Entry> mEntries = new ArrayList<>();
        if (list.size() != 0){
            for (ChartBean.ChartData bean : list) {
                String time = bean.getTime().split("-")[1];
                mEntries.add(new Entry(Float.parseFloat(time), Float.parseFloat(bean.getValue())));
            }
        }
        Collections.reverse(mEntries);
        LineDataSet mLineDataSet = new LineDataSet(mEntries, type);
        mLineDataSet.setColor(color);
        return mLineDataSet;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectDay(DateEvent event){
        if ("year".equals(event.getType())){
            selectYear = event.getDate();
            btnSelectDay.setText(selectYear);
            initCharts();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
