package com.xczn.smos.ui.fragment.home.equiip;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Equipment1;
import com.xczn.smos.event.EquipDrawEvent;
import com.xczn.smos.request.EquipmentService;
import com.xczn.smos.utils.ArcgisUtils;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class EquipFragment extends BaseBackFragment {

    private static final String TAG = EquipFragment.class.getSimpleName();
    private MapView mapView;

    private List<Equipment1> equipment1List = new ArrayList<>();

    public static EquipFragment newInstance() {
        return new EquipFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equip, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        initMap(view);
        initData();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initMap(View view) {

        mapView = view.findViewById(R.id.map_view);
//        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer("http://111.204.100.179:6080/arcgis/rest/services/china2012/MapServer");
//        Basemap basemap = new Basemap(tiledLayer);
//        ArcGISMap map = new ArcGISMap(basemap);
//        mapView.setMap(map);
//        Point centerPoint = new Point(113.868,34.090,SpatialReference.create(4326));
//        mapView.setViewpointCenterAsync(centerPoint, 200000);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.090, 113.868, 10);
        mapView.setMap(map);

        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(_mActivity ,mapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Equipment1 equipment1 = ArcgisUtils.getEquipment(mapView, e ,equipment1List);
                if (equipment1 != null){
                    Point point = ArcgisUtils.getPoint(equipment1);
                    mapView.setViewpointCenterAsync(point,14);
                    showEquipmentDialog(equipment1);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void initView(View view) {
        Button btnEquipSearch = view.findViewById(R.id.btn_equip_search);
        btnEquipSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(EquipSearchFragment.newInstance(convertListToArrayList(equipment1List)));
            }
        });
    }

    private void initData() {
        Observer<List<Equipment1>> observer= new Observer<List<Equipment1>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(List<Equipment1> equipment1s) {
                equipment1List = equipment1s;
                Log.d(TAG, "onNext: " + equipment1List.size());
                GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                List<Graphic> graphics = ArcgisUtils.drawPoints(equipment1List);
                Log.d(TAG, "onNext: " + graphics.size());
                graphicsOverlay.getGraphics().addAll(graphics);
                mapView.getGraphicsOverlays().add(graphicsOverlay);
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
        service.getEquipment1List()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer);
    }

    private void showEquipmentDialog(final Equipment1 equipment1){
        new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_equipment)    //设置弹窗展示的xml布局
                .setScreenWidthAspect(_mActivity, 1.0f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.BOTTOM)//设置弹窗展示位置
                .setDimAmount(0f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .setCancelable(true)    //弹窗是否可以取消
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.tv_equip_name, equipment1.getEquipname() + "-" + equipment1.getEquipid());
                        viewHolder.setText(R.id.tv_equip_des, equipment1.getType());
                    }
                })
                .create()   //创建TDialog
                .show();    //展示
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void drawEquip(EquipDrawEvent event) {
        Equipment1 equip = event.getEquipment1();
        if (equip.getEquiplongitude().equals("") || equip.getEquiplatitude().equals("")){
            ToastUtils.showShortToast(_mActivity, equip.getEquipname()+"-"+equip.getEquipid()+"没有坐标信息！");
        } else {
            Point point = ArcgisUtils.getPoint(equip);
            mapView.setViewpointCenterAsync(point, 14);

//            if (locationGraphics != null) {
//                mapView.getGraphicsOverlays().remove(locationGraphics);
//            }
            BitmapDrawable location = (BitmapDrawable) _mActivity.getResources().getDrawable(R.drawable.ic_equip_searched);
            GraphicsOverlay locationGraphics = new GraphicsOverlay();
            locationGraphics.getGraphics().add(ArcgisUtils.drawPoint(point, location));
            mapView.getGraphicsOverlays().add(locationGraphics);
        }
    }

    /**
     * 将未知类型list转成arraylist
     * @param orig 参数
     * @param <T> 类型
     * @return ArrayList
     */
    public static <T> ArrayList<T> convertListToArrayList(List<T> orig) {
        //如果为null直接返回，这里也可以把size=0加上
        if (null == orig) return null;
        if (orig instanceof ArrayList) {//判断是否就是ArrayList,如果是，则强转
            return (ArrayList)orig;
        } else {
            ArrayList<T> returnValue = new ArrayList<>(orig.size());
            for (T t : orig) {
                returnValue.add(t);
            }
            //jdk1.8及以上可以使用这样的循环遍历
            //orig.forEach(t -> returnValue.add(t));
            return returnValue;
        }
    }
}
