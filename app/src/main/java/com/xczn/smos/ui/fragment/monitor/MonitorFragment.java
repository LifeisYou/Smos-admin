package com.xczn.smos.ui.fragment.monitor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.xczn.smos.R;
import com.xczn.smos.entity.Equipment2Tree;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.holder.EquipmentViewHolder;
import com.xczn.smos.holder.FactoryViewHolder;
import com.xczn.smos.holder.IntervalViewHolder;
import com.xczn.smos.request.EquipmentService;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.utils.HttpMethods;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment
 */

public class MonitorFragment extends SupportFragment{
    private AndroidTreeView tView;
    private static ViewGroup containerView;
    private List<Equipment2Tree> equipment2TreeList = new ArrayList<>();
    private TreeNode root;

    public static MonitorFragment newInstance() {
        return new MonitorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        containerView = view.findViewById(R.id.container);
        root = TreeNode.root();
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("变电站");
    }

    //初始化设备树形结构
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLogin(LoginEvent event){
        UserList mUser = event.user;
        if (mUser ==null || "".equals(mUser.getType())){
            containerView.setVisibility(View.INVISIBLE);
        } else {
            containerView.setVisibility(View.VISIBLE);        }
    }

    private void addFactory(TreeNode root, List<Equipment2Tree> systemEquipments){
        if (!systemEquipments.isEmpty()){
            for (Equipment2Tree systemEquipment :systemEquipments){
                TreeNode factoryNode = new TreeNode(new FactoryViewHolder.FactoryItem(systemEquipment.getFactory_name())).setViewHolder(new FactoryViewHolder(_mActivity));
                addInterval(factoryNode, systemEquipment.getIntervals());
                root.addChild(factoryNode);
            }
        }
    }

    private void addInterval(TreeNode root, List<Equipment2Tree.IntervalsBean> Intervals){
        if (!Intervals.isEmpty()){
            for (Equipment2Tree.IntervalsBean interval :Intervals){
                TreeNode intervalNode = new TreeNode(new IntervalViewHolder.IntervalItem(interval.getInterval_name())).setViewHolder(new IntervalViewHolder(_mActivity));
                addEquipment(intervalNode, interval.getEquipments());
                root.addChild(intervalNode);
            }
        }
    }

    private void addEquipment(TreeNode root, List<Equipment2Tree.IntervalsBean.EquipmentsBean> Equipments){
        if (!Equipments.isEmpty()){
            for (final Equipment2Tree.IntervalsBean.EquipmentsBean equipment :Equipments){
                TreeNode factoryNode = new TreeNode(new EquipmentViewHolder.EquipmentItem(equipment.getEquipment_name())).setViewHolder(new EquipmentViewHolder(_mActivity));
                factoryNode.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                        EquipmentViewHolder.EquipmentItem item = (EquipmentViewHolder.EquipmentItem) value;
                        ((MainFragment) getParentFragment()).start(MeasureFragment.newInstance(equipment));
                    }
                });
                root.addChild(factoryNode);
            }
        }
    }

    @SuppressLint("CheckResult")
    private void initData() {
        HttpMethods.getRetrofit().create(EquipmentService.class)
                .getEquipment2Tree()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Equipment2Tree>>() {
                               @Override
                               public void accept(List<Equipment2Tree> myEquips) {
                                   equipment2TreeList = myEquips;
                                   if (equipment2TreeList.size() != 0) {
                                       addFactory(root, equipment2TreeList);
                                       tView = new AndroidTreeView(_mActivity, root);
                                       //tView.collapseAll();
                                       tView.setDefaultAnimation(true);
                                       tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
                                       containerView.addView(tView.getView());
                                   }
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                            }
                        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tView != null) {
            outState.putString("tState", tView.getSaveState());
        }
    }
}
