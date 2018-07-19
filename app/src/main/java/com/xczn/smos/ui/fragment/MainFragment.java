package com.xczn.smos.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseMainFragment;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.AlarmPostEvent;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.event.TabSelectedEvent;
import com.xczn.smos.request.AccountService;
import com.xczn.smos.ui.fragment.home.HomeFragment;
import com.xczn.smos.ui.fragment.monitor.MonitorFragment;
import com.xczn.smos.ui.fragment.setting.SetFragment;
import com.xczn.smos.ui.view.BottomBar;
import com.xczn.smos.ui.view.BottomBarTab;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 主界面MainFragment
 */

public class MainFragment extends BaseMainFragment {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];

    private BottomBar mBottomBar;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initLogin();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = MonitorFragment.newInstance();
            mFragments[THIRD] = SetFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(MonitorFragment.class);
            mFragments[THIRD] = findChildFragment(SetFragment.class);
        }
    }

    @SuppressLint("CheckResult")
    private void initLogin() {
        final String username = SharedPreferencesUtils.getInstance().getLoginUsername();
        final String password = SharedPreferencesUtils.getInstance().getLoginPassword();
        if ("".equals(username) || "".equals(password)) {
            EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(null));
            Toast.makeText(_mActivity, "请用户先登录！", Toast.LENGTH_SHORT).show();
        } else {
            HttpMethods.getRetrofit().create(AccountService.class)
                    .login(username, password)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UserList>() {
                                   @Override
                                   public void accept(UserList user) {
                                       if (user == null || "".equals(user.getType())) {
                                           //**** failed
                                           EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(null));
                                           Toast.makeText(_mActivity, "请用户先登录！", Toast.LENGTH_SHORT).show();
                                       } else if ("admin".equals(user.getType())) {
                                           SharedPreferencesUtils.getInstance().setLoginUsername(user.getUserId());
                                           EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(user));
                                       } else if ("user".equals(user.getType())) {
                                           SharedPreferencesUtils.getInstance().setLoginUsername(user.getUserId());
                                           EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(user));
                                       }
                                   }
                               }
                            , new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(null));
                                    Toast.makeText(_mActivity, "请用户先登录！", Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }
    private void initView(View view) {
        mBottomBar = view.findViewById(R.id.bottomBar);
        EventBusActivityScope.getDefault(_mActivity).register(this);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_home, getString(R.string.home)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, getString(R.string.monitor)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_me, getString(R.string.setting)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
    }

    @Subscribe
    public void getAlarmNumber(AlarmPostEvent event){
        // 模拟未读消息
        mBottomBar.getItem(FIRST).setUnreadCount( mBottomBar.getItem(FIRST).getUnreadCount() + event.getNumber()    );
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}

