package com.xczn.smos.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xczn.smos.R;
import com.xczn.smos.adapter.MenuAdapter;
import com.xczn.smos.entity.MenuItem;
import com.xczn.smos.entity.StationMsgBean;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.request.EquipmentService;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.utils.GlideImageLoader;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 主页面
 */

public class HomeFragment extends SupportFragment {

    private static final String TAG = HomeFragment.class.getName();
    private Banner mBanner;
    private TextView tvStationMsg,tvBayMsg,tvSecondaryEquipMsg,tvEquipMsg;
    private ArrayList<String> images = new ArrayList<>();
    private List<MenuItem> menuItems = new ArrayList<>();
    private MenuAdapter homeMenuAdapter;
    private boolean ItemClickable = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        initStationMsg();
        return view;
    }

    private void initView(View view) {
        //获得banner的轮播图ip
        String baseUrl = SharedPreferencesUtils.getInstance().getBaseUrl();
        images.add(baseUrl + "banners?id=first.jpg");
        images.add(baseUrl + "banners?id=second.jpg");
        images.add(baseUrl + "banners?id=third.jpg");
        images.add(baseUrl + "banners?id=fourth.jpg");
        //初始化banner，轮播图插件
        mBanner = view.findViewById(R.id.banner);
        setBean(images);
        //菜单栏初始化
        GridView menuGridView = view.findViewById(R.id.home_grid_menu);
        menuItems = MenuItem.getAdminMenuList();
        homeMenuAdapter = new MenuAdapter(_mActivity, menuItems);
        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ItemClickable) {
                    assert getParentFragment() != null;
                    ((MainFragment) getParentFragment()).start(menuItems.get(i).getFragment());
                } else {
                    Toast.makeText(_mActivity, "请用户先登录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        menuGridView.setAdapter(homeMenuAdapter);
        //变电站信息控件初始化
        tvStationMsg = view.findViewById(R.id.tv_station_msg);
        tvBayMsg = view.findViewById(R.id.tv_bay_msg);
        tvSecondaryEquipMsg = view.findViewById(R.id.tv_secondar_equip_msg);
        tvEquipMsg = view.findViewById(R.id.tv_equip_msg);
    }

    //初始化首页功能菜单
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLogin(LoginEvent event){
        menuItems.clear();
        UserList mUser = event.user;
        if (mUser == null || "".equals(mUser.getType())){
            menuItems.addAll(MenuItem.getAllMenuList());
            ItemClickable = false;
            Log.d(TAG, "setLogin: " + "gridView setClickable false");
        } else if ("admin".equals(mUser.getType())){
            menuItems.addAll(MenuItem.getAdminMenuList());
            ItemClickable = true;
            Log.d(TAG, "setLogin: " + "gridView setClickable true");
        } else if ("user".equals(mUser.getType())){
            menuItems.addAll(MenuItem.getUserMenuList());
            ItemClickable = true;
        }
        homeMenuAdapter.notifyDataSetChanged();
    }

    private void setBean(final ArrayList beans) {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(beans);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }


    @SuppressLint("CheckResult")
    private void initStationMsg() {
        HttpMethods.getRetrofit().create(EquipmentService.class)
                .getStationMsg()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StationMsgBean>>() {
                               @SuppressLint("SetTextI18n")
                               @Override
                               public void accept(List<StationMsgBean> stationMsgBeans) {
                                   tvStationMsg.setText(stationMsgBeans.get(0).getName() + ":"
                                           + stationMsgBeans.get(0).getCount() +"座");
                                   tvBayMsg.setText(stationMsgBeans.get(1).getName() + ":"
                                           + stationMsgBeans.get(1).getCount() +"个");
                                   tvSecondaryEquipMsg.setText(stationMsgBeans.get(2).getName() + ":"
                                           + stationMsgBeans.get(2).getCount() +"台");
                                   tvEquipMsg.setText(stationMsgBeans.get(3).getName() + ":"
                                           + stationMsgBeans.get(3).getCount() +"台");
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {

                            }
                        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}

