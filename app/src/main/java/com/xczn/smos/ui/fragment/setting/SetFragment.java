package com.xczn.smos.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suke.widget.SwitchButton;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.xczn.smos.R;
import com.xczn.smos.entity.User;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.AvatarEvent;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.mqtt.AlarmActionListener;
import com.xczn.smos.mqtt.AlarmMqttCallback;
import com.xczn.smos.mqtt.ConnectionModel;
import com.xczn.smos.request.UsersService;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.ui.fragment.account.LoginFragment;
import com.xczn.smos.utils.GlideUtils;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.ToastUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment Setting 设置页面
 */

public class SetFragment extends SupportFragment implements View.OnClickListener{

    private static final String AlarmTopic = "Alarm";

    private CircleImageView ivAvatar;
    private TextView tvUsername;
    private TextView tvName;
    private TextView tvPhone;
    private Button btnUserMessage;
    private SwitchButton sbAlarm;
    private RelativeLayout rlSubAlarm, rlExit, rlUserInfo, rlUserLogin;

    private UserList mUser;
    private MqttAndroidClient client;

    public static SetFragment newInstance() {
        return new SetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.server_btn:
                startFragment(ServerFragment.newInstance());
                break;
            case R.id.login_or_register:
                startFragment(LoginFragment.newInstance());
                break;
            case R.id.exit_btn:
                logout();
                break;
            case R.id.user_btn:
                startFragment(UserMessageFragment.newInstance(mUser.getUserId()));
                break;
            case R.id.soft_btn:
                startFragment(SoftMessageFragment.newInstance());
                break;
            case R.id.about_btn:
                startFragment(AboutFragment.newInstance());
                break;
            case R.id.user_avatar:
                startFragment(AvatarPreviewFragment.newInstance(mUser.getUserId()+".jpg"));
                break;
            default:
                break;
        }
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("设置");

        tvUsername = view.findViewById(R.id.user_username);
        tvName = view.findViewById(R.id.user_name);
        tvPhone = view.findViewById(R.id.user_phone);
        rlSubAlarm = view.findViewById(R.id.layout_subscribe_alarm);
        rlExit = view.findViewById(R.id.rl_exit);
        rlUserInfo = view.findViewById(R.id.rl_user_info);
        rlUserLogin = view.findViewById(R.id.rl_user_login);

        TextView tvLoginOrRegister = view.findViewById(R.id.login_or_register);
        tvLoginOrRegister.setOnClickListener(this);

        Button btnExit = view.findViewById(R.id.exit_btn);
        btnExit.setOnClickListener(this);

        btnUserMessage = view.findViewById(R.id.user_btn);
        btnUserMessage.setOnClickListener(this);
        btnUserMessage.setClickable(false);

        Button serverBtn = view.findViewById(R.id.server_btn);
        serverBtn.setOnClickListener(this);
        Button btnSoftMsg = view.findViewById(R.id.soft_btn);
        btnSoftMsg.setOnClickListener(this);
        Button btnAbout = view.findViewById(R.id.about_btn);
        btnAbout.setOnClickListener(this);

        ivAvatar = view.findViewById(R.id.user_avatar);
        ivAvatar.setOnClickListener(this);

        //初始化报警订阅，连接MQTT服务
        sbAlarm = view.findViewById(R.id.sb_alarm);
        sbAlarm.setChecked(false);
        sbAlarm.setEnabled(false);     //设置订阅alarm的switchButton不可点击
        sbAlarm.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    subscribeAlarm();
                } else {
                    unsubscribeAlarm();
                }
            }
        });
    }

    //订阅登录事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLogin(LoginEvent event){
        mUser = event.user;

        if (mUser == null || "".equals(mUser.getType())) {
            rlUserInfo.setVisibility(View.GONE);
            rlUserLogin.setVisibility(View.VISIBLE);
            rlSubAlarm.setVisibility(View.VISIBLE);
            rlExit.setVisibility(View.GONE);
            btnUserMessage.setClickable(false);
            sbAlarm.setChecked(false);
            sbAlarm.setEnabled(false);
            disconnectClient();
        } else {
            String urlAvatar = SharedPreferencesUtils.getInstance().getBaseUrl() + "avatars?id=" + mUser.getUserId() + ".jpg";
            GlideUtils.loadImageNoCache(_mActivity ,urlAvatar, ivAvatar);

            rlUserLogin.setVisibility(View.GONE);
            rlUserInfo.setVisibility(View.VISIBLE);
            tvUsername.setText(mUser.getUserId());
            tvName.setText(mUser.getName());
            tvPhone.setText(mUser.getPhone());
            rlExit.setVisibility(View.VISIBLE);
            btnUserMessage.setClickable(true);

            if ("admin".equals(mUser.getType())) {
                rlSubAlarm.setVisibility(View.VISIBLE);
                sbAlarm.setEnabled(true);
                if (SharedPreferencesUtils.getInstance().getAlarmSubscribeStatus(mUser.getUserId())) {
                    resubscribeAlarm();
                    sbAlarm.setChecked(true);
                } else {
                    sbAlarm.setChecked(false);
                }
            } else {
                rlSubAlarm.setVisibility(View.GONE);
            }
        }
    }

    //已经订阅的订阅报警服务
    private void resubscribeAlarm(){
        String url = "tcp://"+SharedPreferencesUtils.getInstance().getServerIp()+":1883";
        String clientId = "soms-" + mUser.getUserId();
        AlarmMqttCallback callback = new AlarmMqttCallback(_mActivity);
        client = new MqttAndroidClient(_mActivity, url, clientId);
        client.setCallback(callback);
        if (client != null){
            try {
                final AlarmActionListener listener = new AlarmActionListener(_mActivity, client);
                MqttConnectOptions options = ConnectionModel.getOptions();
                client.connect(options,null, listener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    //订阅报警服务
    private void subscribeAlarm(){
        String url = "tcp://"+SharedPreferencesUtils.getInstance().getServerIp()+":1883";
        String clientId = "soms-" + mUser.getUserId();
        AlarmMqttCallback callback = new AlarmMqttCallback(_mActivity);
        client = new MqttAndroidClient(_mActivity, url, clientId);
        client.setCallback(callback);
        if (client != null){
            try {
                final AlarmActionListener listener = new AlarmActionListener(_mActivity, client);
                MqttConnectOptions options = ConnectionModel.getOptions();
                client.connect(options,null, listener);
                if (SharedPreferencesUtils.getInstance().setAlarmSubscribeStatus(mUser.getUserId(), true)) {
                    ToastUtils.showShortToast(_mActivity, "报警订阅成功 ！");
                }
            } catch (MqttException e) {
                e.printStackTrace();
                ToastUtils.showShortToast(_mActivity, "报警订阅出错，请重试 ！");
            }
        } else {
            ToastUtils.showShortToast(_mActivity, "报警订阅出错，请重试 ！");
        }
    }

    //取消订阅报警
    private void unsubscribeAlarm(){
        if (client != null) {
            try {
                client.unsubscribe(AlarmTopic);
                disconnectClient();
                SharedPreferencesUtils.getInstance().setAlarmSubscribeStatus(mUser.getUserId(), false);
                ToastUtils.showShortToast(_mActivity, "取消报警订阅成功！");
            } catch (MqttException e) {
                e.printStackTrace();
                ToastUtils.showShortToast(_mActivity, "取消报警订阅失败,请重试！");
            }
        }
    }

    //MQTT服务器client 断开连接
    private void disconnectClient(){
        if (client != null) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    //用户注销,弹窗
    private void logout(){
        new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_exit)                  //设置弹窗展示的xml布局
                .setScreenWidthAspect(_mActivity, 1.0f)  //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.BOTTOM)                          //设置弹窗展示位置
                .setDimAmount(0.6f)                                  //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)                          //弹窗在界面外是否可以点击取消
                .setCancelable(true)                                 //弹窗是否可以取消
                .addOnClickListener(R.id.tv_yes, R.id.tv_cancel)     //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {  //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_yes:
                                SharedPreferencesUtils.getInstance().setLoginInfo("", "");
                                startFragment(ExitFragment.newInstance());
                                tDialog.dismiss();
                                break;
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                        }
                    }
                })
                .create()                                            //创建TDialog
                .show();                                             //展示
    }



    //上传头像后更新头像和基本信息
    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAvatar(AvatarEvent event){
        if (event.isUpdate()){
            String urlAvatar = SharedPreferencesUtils.getInstance().getBaseUrl() + "avatars?id=" + mUser.getUserId() + ".jpg";
            GlideUtils.loadImageNoCache(_mActivity ,urlAvatar, ivAvatar);
            HttpMethods.getRetrofit().create(UsersService.class)
                    .getUserInfo(mUser.getUserId())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<User>() {
                        @Override
                        public void accept(User user) {
                            tvName.setText(user.getName());
                            tvPhone.setText(user.getPhone());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {}
                    });
        }
    }

    private void startFragment(SupportFragment fragment){
        SupportFragment mFragment = (MainFragment) getParentFragment();
        if (mFragment != null) {
            mFragment.start(fragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}