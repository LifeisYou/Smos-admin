package com.xczn.smos.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xczn.smos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/22.
 */

public class CalendarYear extends LinearLayout {

    /**
     * 年
     */
    private SpinnerView mYearSpinnerView;
    /**
     *  文本颜色
     */
    private int mTextColor;

    /**
     *  文本大小
     */
    private int mTextSize;

    /**
     * 选中文本的颜色
     */
    private int mSelectedTextColor;

    /**
     *  选中线的颜色
     */
    private int mSelectedLineColor;

    /**
     *  选择的年, 月，日
     */
    private int mSelectYear;

    /**
     *  时间Linearlayout
     */
    private LinearLayout mLlDate;

    private OnDateSelectedListener mOnDateSelectedListener;

    public CalendarYear(Context context) {
        super(context);
    }

    public CalendarYear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CalendarYear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarYear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        mOnDateSelectedListener = onDateSelectedListener;
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        initStyle(context, attrs);

        /**
         *  时间linearlayout布局
         */
        LayoutParams dateLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLlDate = new LinearLayout(context);
        mLlDate.setOrientation(HORIZONTAL);
        mLlDate.setLayoutParams(dateLp);
        addView(mLlDate);

        View divisionLineView = new View(context);
        divisionLineView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        divisionLineView.setBackgroundColor(mSelectedLineColor);
        addView(divisionLineView);

        /**
         *  添加按钮布局
         */
        TextView tvSure = new TextView(context);
        tvSure.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tvSure.setGravity(Gravity.CENTER_HORIZONTAL);
        tvSure.setTextSize(16);
        tvSure.setPadding(0, 25, 0, 25);
        tvSure.setText("确定");
        addView(tvSure);
        tvSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnDateSelectedListener != null) {
                    mOnDateSelectedListener.onDateSelected(mSelectYear);
                }
            }
        });

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, 800);
        mYearSpinnerView = new SpinnerView(context);
        addViewData(mYearSpinnerView, lp, getYearList(), "年");

        setListener();
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarViewStyle);
        mTextColor = typedArray.getColor(R.styleable.CalendarViewStyle_textColor, Color.parseColor("#999999"));
        mSelectedTextColor = typedArray.getColor(R.styleable.CalendarViewStyle_selectedTextColor, Color.parseColor("#3F51B5"));
        mSelectedLineColor = typedArray.getColor(R.styleable.CalendarViewStyle_selectedLineColor, Color.parseColor("#D6D6D6"));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CalendarViewStyle_selectedTextColor, 50);
        typedArray.recycle();
    }

    private void setStyle(SpinnerView spinnerView) {
        spinnerView.setTextColor(mTextColor);
        spinnerView.setTextSize(mTextSize);
        spinnerView.setSelectedTextColor(mSelectedTextColor);
        spinnerView.setSelectLineColor(mSelectedLineColor);
        spinnerView.invalidate();
    }

    private void addViewData(SpinnerView spinnerView, LayoutParams lp, List<String> dataList, String unit) {
        spinnerView.setLayoutParams(lp);
        mLlDate.addView(spinnerView);
        spinnerView.setAllDataList(dataList);
        spinnerView.setUnit(unit);

        setStyle(spinnerView);
    }

    private void setListener() {
        mYearSpinnerView.setOnDataSelectedListener(new SpinnerView.OnDataSelectedListener() {
            @Override
            public void onSelected(int data) {
                mSelectYear = data;
            }
        });
    }

    public void setDate(String date) {
        String[] dateArray = date.split("-");
        switch (dateArray.length) {
            case 3:
                mSelectYear = Integer.valueOf(dateArray[0]);
                break;
            case 2:
                mSelectYear = Integer.valueOf(dateArray[0]);
                break;
            case 1:
                break;
        }
        mYearSpinnerView.setCurrentData(mSelectYear + "年");
    }

    private List<String> getYearList() {
        List<String> dataList = new ArrayList<>();
        for(int i = 1970; i < 2035; i++) {
            dataList.add(i + "年");
        }
        return dataList;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(int year);
    }

}
