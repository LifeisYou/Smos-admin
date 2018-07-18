package com.xczn.smos.ui.fragment.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xczn.smos.MainActivity;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.event.TaskUpdateEvent;
import com.xczn.smos.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * @Author zhangxiao
 * @Date 2018/4/18 0018
 * @Comment 服务器
 */

public class ServerFragment extends BaseBackFragment implements View.OnClickListener{

    private EditText serverIp,serverPort;


    public static ServerFragment newInstance() {
        return new ServerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus 注册
//        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        serverIp = view.findViewById(R.id.server_ip);
        serverPort = view.findViewById(R.id.server_port);
        Button serverSubmit = view.findViewById(R.id.server_submit_btn);
        serverSubmit.setOnClickListener(this);

        //填写ip信息
        serverIp.setText(SharedPreferencesUtils.getInstance().getServerIp());
        //填写port信息
        serverPort.setText(SharedPreferencesUtils.getInstance().getServerPort());

        mToolbar.setTitle("服务器信息");
        initToolbarNav(mToolbar);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.server_submit_btn:
                //关闭软键盘
                hideSoftInput();
                if (submitServerMsg()){
                    //_Activity将会重启
                    Toast.makeText(_mActivity, "提交成功", Toast.LENGTH_SHORT).show();
                    // _mActivity.onBackPressed();
                    _mActivity.finish();
                    _mActivity.startActivity(_mActivity.getIntent());
                    //杀死应用所有进程
                    android.os.Process.killProcess(android.os.Process.myPid());

//                    Intent i = _mActivity.getPackageManager().getLaunchIntentForPackage("com.xczn.smos");
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    _mActivity.startActivity(i);

//                    Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);

//                    restartApp();
                }else {
                    Toast.makeText(_mActivity, "提交失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                //EventBus 发送时间
                //EventBus.getDefault().post(new TaskUpdateEvent(0));
                break;
        }
    }

    private boolean submitServerMsg() {
        String ip = serverIp.getText().toString();
        String port = serverPort.getText().toString();
        return SharedPreferencesUtils.getInstance().setServer(ip, port);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //EventBus 注销
//        if (EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this);
//        }
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    public void restartApp() {
        //启动页
        Intent intent = new Intent(_mActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _mActivity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
