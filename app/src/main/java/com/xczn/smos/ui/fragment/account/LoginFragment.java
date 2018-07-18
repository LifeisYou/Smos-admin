package com.xczn.smos.ui.fragment.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.timmy.tdialog.TDialog;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.request.AccountService;
import com.xczn.smos.ui.fragment.MainFragment;
import com.xczn.smos.ui.view.ProgressDialog;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.ToastUtils;

import java.util.Timer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 登录页面
 */

public class LoginFragment extends BaseBackFragment {

    private static final String TAG = LoginFragment.class.getName();
    private static final int TEXT_PASSWORD = 129;
    private static final int REQUEST_CODE_REGISTER = 11;

    private EditText usernameEt;
    private EditText passwordEt;
    private ImageView ivPw;
    private TDialog loginDialog;

    Timer timer = new Timer();
    ProgressDialog progressDialog = ProgressDialog.create();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        initToolbarNav(toolbar);

        Button loginBtn = view.findViewById(R.id.login_btn);
        usernameEt = view.findViewById(R.id.login_username);
        passwordEt = view.findViewById(R.id.login_password);

        String username = SharedPreferencesUtils.getInstance().getLoginUsername();
        String password = SharedPreferencesUtils.getInstance().getLoginPassword();
        Log.d(TAG, "initView: " + username +"+"+ password);
        usernameEt.setText(username);
        passwordEt.setText(password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(usernameEt.getText().toString()) || "".equals(passwordEt.getText().toString())){
                    ToastUtils.showShortToast(_mActivity, "用户名或密码不能为空！");
                } else {
                    //关闭软键盘
                    hideSoftInput();
                    loginDialog();
                    loginServer();
                }
            }
        });

        TextView tvRegister = view.findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(RegisterFragment.newInstance());
            }
        });

        ivPw = view.findViewById(R.id.iv_see_password);
        passwordEt.setInputType(TEXT_PASSWORD);
        ivPw.setBackgroundResource(R.drawable.ic_unsee_pw);
        ivPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEt.getInputType() == TEXT_PASSWORD){
                    passwordEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    ivPw.setBackgroundResource(R.drawable.ic_see_pw);
                } else if (passwordEt.getInputType() == InputType.TYPE_CLASS_TEXT){
                    passwordEt.setInputType(TEXT_PASSWORD);
                    ivPw.setBackgroundResource(R.drawable.ic_unsee_pw);
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void loginServer() {
        final String usernameStr = usernameEt.getText().toString();
        final String passwordStr = passwordEt.getText().toString();

        HttpMethods.getRetrofit().create(AccountService.class)
                .login(usernameStr, passwordStr)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserList>() {
                               @Override
                               public void accept(UserList userList) {
                                   loginDialog.dismiss();
                                   if (userList.getUserId().equals("")) {
                                       //**** failed
                                       Toast.makeText(_mActivity, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                                   } else if ("admin".equals(userList.getType())){
                                       if (SharedPreferencesUtils.getInstance().setLoginUsername(usernameStr)
                                               && SharedPreferencesUtils.getInstance().setLoginPassword(passwordStr)){
                                           //ToastUtils.showShortToast(_mActivity, "初始化错误，请稍后重新登录");
                                       }
                                       EventBusActivityScope.getDefault(_mActivity).post( new LoginEvent(userList));
                                       _mActivity.onBackPressed();
                                   } else if ("user".equals(userList.getType())){
                                       if (!SharedPreferencesUtils.getInstance().setLoginUsername(usernameStr)
                                               && !SharedPreferencesUtils.getInstance().setLoginPassword(passwordStr)){
                                           //ToastUtils.showShortToast(_mActivity, "初始化错误，请稍后重新登录");
                                       }
                                       EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(userList));
                                       _mActivity.onBackPressed();
                                   }
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                loginDialog.dismiss();
                                ToastUtils.showLongToast(_mActivity, "登录错误，请稍后重试");
                            }
                        });
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    progressDialog.dismiss();
                    start(MainFragment.newInstance());
                    timer.cancel();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    public void loginDialog(){
        loginDialog = new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_uploading)    //设置弹窗展示的xml布局
                .setScreenWidthAspect(_mActivity, 1.0f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setScreenHeightAspect(_mActivity, 1.0f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
//                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
//                .setCancelable(true)    //弹窗是否可以取消
                .create()  //创建TDialog
                .show();    //展示
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            _mActivity.onBackPressed();
        }
    }
}
