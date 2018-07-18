package com.xczn.smos.entity;

import com.xczn.smos.R;
import com.xczn.smos.ui.fragment.home.TestFragment;
import com.xczn.smos.ui.fragment.home.alarm.AlarmFragment;
import com.xczn.smos.ui.fragment.home.analysis.AnalysisFragment;
import com.xczn.smos.ui.fragment.home.equiip.EquipFragment;
import com.xczn.smos.ui.fragment.home.SvgFragment;
import com.xczn.smos.ui.fragment.home.taks_user.TaskUserFragment;
import com.xczn.smos.ui.fragment.home.user.UsersFragment;
import com.xczn.smos.ui.fragment.home.task.TaskFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhangxiao
 * Date on 2018/5/3.
 */
public class MenuItem {

    private int icon;
    private String menu;
    private SupportFragment fragment;

    public MenuItem(int icon, String menu, SupportFragment fragment) {
        this.icon = icon;
        this.menu = menu;
        this.fragment = fragment;
    }

    public int getIcon() {

        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public SupportFragment getFragment() {
        return fragment;
    }

    public void setFragment(SupportFragment fragment) {
        this.fragment = fragment;
    }

    public static List<MenuItem> getAdminMenuList(){
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_alarm, "实时报警", AlarmFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_task, "我的任务", TaskFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_map, "设备分布", EquipFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_user, "运维人员", UsersFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_svg, "厂站监控", TestFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_analysis, "电力图表", AnalysisFragment.newInstance()));
        return menuItemList;
    }

    public static List<MenuItem> getUserMenuList(){
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_task, "我的任务", TaskUserFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_map, "设备分布", EquipFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_user, "运维人员", UsersFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_svg, "厂站监控", SvgFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_analysis, "电力图表", AnalysisFragment.newInstance()));
        return menuItemList;
    }

    public static List<MenuItem> getAllMenuList(){
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_alarm, "实时报警", AlarmFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_task, "我的任务", TaskFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_map, "设备分布", EquipFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_user, "运维人员", UsersFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_svg, "厂站监控", SvgFragment.newInstance()));
        menuItemList.add(new MenuItem(R.drawable.ic_menu_list_analysis, "电力图表", AnalysisFragment.newInstance()));
        return menuItemList;
    }
}
