package com.xczn.smos.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.xczn.smos.entity.ChartBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/7/16 0016
 * @Comment
 */

public class ChartUtils {

    private static final String[] day = new String[]{
            "0.00", "1.00", "2.00", "3.00", "4.00","5.00", "6.00", "7.00",
            "8.00", "9.00", "10.00", "11.00","12.00", "13.00", "14.00", "15.00",
            "16.00", "17.00", "18.00","19.00", "20.00", "21.00", "22.00", "23.00"
    };
    public static void initDayLineChart(LineChart mLineChart, String chartName) {
        //图表名称描述
        Description desP = new Description();
        desP.setText(chartName);
        mLineChart.setDescription(desP);
        mLineChart.setNoDataText("没有数据");

        mLineChart.setScaleYEnabled(false);
        mLineChart.setScaleXEnabled(false);
        //mLineChart.setVisibleXRange(0f, 100f);

        Legend legend = mLineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setFormSize(10f); //图例大小
        legend.setForm(Legend.LegendForm.LINE); //图例形状
        //设置说明文字的大小和颜色
        legend.setTextSize(12f);
        legend.setTextColor(Color.GRAY);

        YAxis axisLeft = mLineChart.getAxisLeft(); //y轴左边标示
        //axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setSpaceTop(20f);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);                // 不显示图表的右边y坐标轴线

        XAxis xAxis = mLineChart.getXAxis();
        // 上面右图的代码，会发现 xAxis.setLabelsToSkip(4); 的效果并没有出现
        xAxis.setLabelCount(8);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(23);
        //xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //x轴设置，0.00,1.00,2.00-----22.00，23.00
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DayXAxisValueFormatter(day));

    }

    public static void initMonthLineChart(LineChart mLineChart, String chartName) {
        //图表名称描述
        Description desP = new Description();
        desP.setText(chartName);
        mLineChart.setDescription(desP);
        mLineChart.setNoDataText("没有数据");

        mLineChart.setScaleYEnabled(false);
        mLineChart.setScaleXEnabled(false);
        //mLineChart.setVisibleXRange(0f, 100f);

        Legend legend = mLineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setFormSize(10f); //图例大小
        legend.setForm(Legend.LegendForm.LINE); //图例形状
        //设置说明文字的大小和颜色
        legend.setTextSize(12f);
        legend.setTextColor(Color.GRAY);

        YAxis axisLeft = mLineChart.getAxisLeft(); //y轴左边标示
        //axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setSpaceTop(20f);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);                // 不显示图表的右边y坐标轴线

        XAxis xAxis = mLineChart.getXAxis();
        // 上面右图的代码，会发现 xAxis.setLabelsToSkip(4); 的效果并没有出现
        xAxis.setLabelCount(8);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(31);
        //xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //x轴设置，0.00,1.00,2.00-----22.00，23.00
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setValueFormatter(new DayXAxisValueFormatter(day));

    }

    public static void initYearLineChart(LineChart mLineChart, String chartName) {
        //图表名称描述
        Description desP = new Description();
        desP.setText(chartName);
        mLineChart.setDescription(desP);
        mLineChart.setNoDataText("没有数据");

        mLineChart.setScaleYEnabled(false);
        mLineChart.setScaleXEnabled(false);
        //mLineChart.setVisibleXRange(0f, 100f);

        Legend legend = mLineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setFormSize(10f); //图例大小
        legend.setForm(Legend.LegendForm.LINE); //图例形状
        //设置说明文字的大小和颜色
        legend.setTextSize(12f);
        legend.setTextColor(Color.GRAY);

        YAxis axisLeft = mLineChart.getAxisLeft(); //y轴左边标示
        //axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setSpaceTop(20f);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);                // 不显示图表的右边y坐标轴线

        XAxis xAxis = mLineChart.getXAxis();
        // 上面右图的代码，会发现 xAxis.setLabelsToSkip(4); 的效果并没有出现
        xAxis.setLabelCount(8);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(12);
        //xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //x轴设置，0.00,1.00,2.00-----22.00，23.00
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setValueFormatter(new DayXAxisValueFormatter(day));

    }

    public static LineDataSet setDayDataSet(List<ChartBean.ChartData> list, int color, String type){
        List<Entry> mEntries = new ArrayList<>();
        if (list.size() != 0){
            for (ChartBean.ChartData bean : list) {
                String time = bean.getTime().split(" ")[1];
                mEntries.add(new Entry(Float.parseFloat(time), Float.parseFloat(bean.getValue())));
            }
        }
        Collections.reverse(mEntries);
        LineDataSet mLineDataSet = new LineDataSet(mEntries, type);
        mLineDataSet.setColor(color);
        //mLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //mLineDataSet.setLineWidth(2f);    //设置线的宽度
        return mLineDataSet;
    }

    static class DayXAxisValueFormatter implements IAxisValueFormatter {
        private String[] str; /** * @param labels 要显示的标签字符数组 */

        DayXAxisValueFormatter(String[] str) {
            this.str = str;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return str[((int) value % str.length)];
        }
    }

}
