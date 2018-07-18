package com.xczn.smos.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import com.xczn.smos.ui.fragment.account.RegisterFragment;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/5/21.
 */

public class ExitFragment extends BaseBackFragment {

    private static final int TEXT_PASSWORD = 129;
    private EditText usernameEt;
    private EditText passwordEt;
    private ImageView ivPw;
    private TDialog loginDialog;

    private UserList mUser = null;

    public static ExitFragment newInstance() {
        return new ExitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    @Override
    public boolean onBackPressedSupport() {
        EventBusActivityScope.getDefault(_mActivity).post(new LoginEvent(mUser));
        return super.onBackPressedSupport();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    private void initView(View view) {
        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.setTitle("注销");
        initToolbarNav(toolbar);

        Button loginBtn = view.findViewById(R.id.login_btn);
        usernameEt = view.findViewById(R.id.login_username);
        passwordEt = view.findViewById(R.id.login_password);

        TextView tvRegister = view.findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(RegisterFragment.newInstance());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();         //关闭软键盘
                loginDialog();
                loginServer();
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
                                       if (!SharedPreferencesUtils.getInstance().setLoginUsername(usernameStr)
                                               && !SharedPreferencesUtils.getInstance().setLoginPassword(passwordStr)){
                                          // ToastUtils.showShortToast(_mActivity, "初始化错误，请稍后重新登录");
                                       }
                                       mUser = userList;
                                       _mActivity.onBackPressed();
                                   } else if ("user".equals(userList.getType())){
                                       if (!SharedPreferencesUtils.getInstance().setLoginUsername(usernameStr)
                                               && !SharedPreferencesUtils.getInstance().setLoginPassword(passwordStr)){
                                          // ToastUtils.showShortToast(_mActivity, "初始化错误，请稍后重新登录");
                                       }
                                       mUser = userList;
                                       _mActivity.onBackPressed();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                loginDialog.dismiss();
                            }
                        });
    }

    public void loginDialog(){
        loginDialog = new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_uploading)    //设置弹窗展示的xml布局
                .setScreenWidthAspect(_mActivity, 1.0f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setScreenHeightAspect(_mActivity, 1.0f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .create()  //创建TDialog
                .show();    //展示
    }
}