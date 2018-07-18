package com.xczn.smos.ui.fragment.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.event.LoginEvent;
import com.xczn.smos.request.AccountService;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.TimeUtils;
import com.xczn.smos.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * @Author zhangxiao
 * @Date 2018/3/2 0002
 * @Comment 注册页面
 */

public class RegisterFragment extends BaseBackFragment {

    private static final String TAG = RegisterFragment.class.getName();

    private EditText etUserId, etName, etPassword, etRePassword, etCode;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("注册");
        initToolbarNav(mToolbar);

        etUserId = view.findViewById(R.id.et_register_user_id);
        etName = view.findViewById(R.id.et_register_name);
        etPassword = view.findViewById(R.id.et_register_password);
        etRePassword = view.findViewById(R.id.et_register_re_password);
        etCode = view.findViewById(R.id.et_register_code);
        Button btnRegister = view.findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                if (verifyString()) {
                    HttpMethods.getRetrofit().create(AccountService.class)
                            .register(etUserId.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etCode.getText().toString(), TimeUtils.getCurrentTime())
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<UserList>() {
                                           @Override
                                           public void accept(UserList user) {
                                               if (user == null || user.getUserId().equals("")) {
                                                   //**** failed
                                                   Toast.makeText(_mActivity, "注册失败，请检查输入内容是否正确！", Toast.LENGTH_SHORT).show();
                                               } else if ("admin".equals(user.getType())) {
                                                   ToastUtils.showShortToast(_mActivity, "注册成功，请登录！");
                                                   _mActivity.onBackPressed();
                                               } else if ("user".equals(user.getType())) {
                                                   ToastUtils.showShortToast(_mActivity, "注册成功，请登录！");
                                                   _mActivity.onBackPressed();
                                               } else if ("repeat".equals(user.getType())){
                                                   Toast.makeText(_mActivity, "注册失败，用户名重复！", Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) {
                                            throwable.printStackTrace();
                                            Log.d(TAG, "accept: register is failed");
                                        }
                                    });
                }
            }
        });
    }

    private boolean verifyString(){
        if (!etUserId.getText().toString().isEmpty() && !etName.getText().toString().isEmpty()
                && !etPassword.getText().toString().isEmpty() && !etCode.getText().toString().isEmpty()) {
            if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                return true;
            } else {
                ToastUtils.showShortToast(_mActivity, "两次密码不同，请检查后重新输入");
                return false;
            }
        } else {
            ToastUtils.showShortToast(_mActivity, "输入内容不能为空，请检查后重新输入");
            return false;
        }
    }
}
