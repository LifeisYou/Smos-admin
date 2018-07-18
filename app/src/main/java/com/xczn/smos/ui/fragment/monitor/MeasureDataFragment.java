package com.xczn.smos.ui.fragment.monitor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xczn.smos.R;
import com.xczn.smos.adapter.MeasureAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.MeasureBean;
import com.xczn.smos.request.EquipmentService;
import com.xczn.smos.utils.HttpMethods;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/7/3.
 */
public class MeasureDataFragment extends BaseBackFragment{

    private RecyclerView rvMeasureData;
    private String equipId;
    private String equipName;
    private MeasureAdapter adapter;

    public static MeasureDataFragment newInstance(String equipId, String equipName) {
        MeasureDataFragment fragment = new MeasureDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("equip_measure", equipId);
        bundle.putString("equip_name", equipName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            equipId = getArguments().getString("equip_measure");
            equipName = getArguments().getString("equip_name");
        } else {
            equipId = "1";
            equipName = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure_data, container, false);
        initView(view);
        getDataTest();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(equipName);
        initToolbarNav(toolbar);

        rvMeasureData = view.findViewById(R.id.rv_measure_data);
        rvMeasureData.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("CheckResult")
    public void getDataTest() {
        HttpMethods.getRetrofit().create(EquipmentService.class)
                .getMeasureData(equipId, 0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MeasureBean>() {
                    @Override
                    public void accept(MeasureBean measureBean) throws Exception {
                        Log.d("测试", "accept: "+ measureBean.getListYc().size());
                        adapter = new MeasureAdapter(measureBean, R.layout.item_measure);
                        rvMeasureData.setLayoutManager(new LinearLayoutManager(_mActivity));
                        rvMeasureData.setAdapter(adapter);
                    }
                });
    }
}
