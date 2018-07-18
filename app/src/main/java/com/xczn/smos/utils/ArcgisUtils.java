package com.xczn.smos.utils;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.xczn.smos.R;
import com.xczn.smos.entity.Equipment1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ArcgisUtils {

    private static final SpatialReference SPATIAL_REFERENCE = SpatialReferences.getWgs84();
    private static final Double ACCURACY = 0.005;

    public static List<Graphic> drawPoints(List<Equipment1> equipment1List){
        List<Graphic> graphics = new ArrayList<>();
        SimpleMarkerSymbol redCircleSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, R.color.newColorPrimary, 5);

        for (Equipment1 equipment1 :equipment1List){
            if (!equipment1.getEquiplatitude().equals("") && !equipment1.getEquiplongitude().equals("")) {
                Point point = new Point(Double.parseDouble(equipment1.getEquiplongitude()), Double.parseDouble(equipment1.getEquiplatitude()), SPATIAL_REFERENCE);
                graphics.add(new Graphic(point, redCircleSymbol));
                TextSymbol equipmentName = new TextSymbol(8, equipment1.getEquipname(),
                        Color.argb(255, 0, 0, 230), TextSymbol.HorizontalAlignment.LEFT, TextSymbol.VerticalAlignment.BOTTOM);
                graphics.add(new Graphic(point, equipmentName));
            }
        }
        return graphics;
    }

    public static Equipment1 getEquipment(MapView mapView, MotionEvent event, List<Equipment1> equipment1List){
        android.graphics.Point screenPoint = new android.graphics.Point(Math.round(event.getX()),
                Math.round(event.getY()));
        Point arcgisPoint = (Point) GeometryEngine.project(mapView.screenToLocation(screenPoint), SPATIAL_REFERENCE);
        for (Equipment1 equipment1 :equipment1List){
            if (!equipment1.getEquiplatitude().equals("") && !equipment1.getEquiplongitude().equals("")) {
                if (Math.abs(arcgisPoint.getX() - Double.parseDouble(equipment1.getEquiplongitude())) < ACCURACY
                        && Math.abs(arcgisPoint.getY() - Double.parseDouble(equipment1.getEquiplatitude())) < ACCURACY) {
                    return equipment1;
                }
            }
        }
        return null;
    }

    public static Equipment1 getClusterEquipment(){

        return null;
    }

    public static Graphic drawPoint(Point point, BitmapDrawable loc) {
        PictureMarkerSymbol marker = new PictureMarkerSymbol(loc);
        return new Graphic(point, marker);
    }

    public static Point getPoint(Equipment1 equip){
        return new Point(Double.parseDouble(equip.getEquiplongitude()), Double.parseDouble(equip.getEquiplatitude()), SPATIAL_REFERENCE);
    }
}
