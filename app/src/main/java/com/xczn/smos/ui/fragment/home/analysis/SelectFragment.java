package com.xczn.smos.ui.fragment.home.analysis;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;
import com.xczn.smos.event.DateEvent;
import com.xczn.smos.ui.view.CalendarView;
import com.xczn.smos.utils.TimeUtils;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/6/22.
 */

public class SelectFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date_picker, null);
        CalendarView calendarView = view.findViewById(R.id.day_select);
        calendarView.setDate(TimeUtils.getCurrentDay());
        calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                String date = addZero(year)+"-" + addZero(month) + "-" + addZero(day);
                EventBusActivityScope.getDefault(getActivity()).post( new DateEvent("day", date));
                dismiss();
            }
        });
        return view;
    }

    private String addZero(int num){
        String date = "";
        if (num < 9){
            date = "0" + String.valueOf(num);
        } else {
            date = String.valueOf(num);
        }
        return date;
    }
}