package com.xczn.smos.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.User;
import com.xczn.smos.event.AvatarEvent;
import com.xczn.smos.request.UsersService;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/6/5.
 */
public class EditUserFragment extends BaseBackFragment {

    private static final String TAG = EditUserFragment.class.getName();
    private static final String EDIT_USER = "edit_user";

    private EditText etEditName;
    private EditText etEditPhone;
    private EditText etEditAddress;
    private User mUser;

    public static EditUserFragment newInstance(User user){
        EditUserFragment fragment = new EditUserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EDIT_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().get(EDIT_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("修改个人信息");
        initToolbarNav(toolbar);

        Button btnSave = view.findViewById(R.id.btn_save_user);
        etEditName = view.findViewById(R.id.et_edit_user_name);
        etEditPhone = view.findViewById(R.id.et_edit_user_phone);
        etEditAddress = view.findViewById(R.id.et_edit_user_address);
        if (mUser != null) {
            etEditName.setText(mUser.getName());
            etEditPhone.setText(mUser.getPhone());
            etEditAddress.setText(mUser.getAddress());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                String name = etEditName.getText().toString();
                String phone = etEditPhone.getText().toString();
                String address = etEditAddress.getText().toString();
                if (name.equals("") || phone.equals("") || address.equals("")){
                    ToastUtils.showShortToast(_mActivity, "不能为空");
                } else {
                    HttpMethods.getRetrofit().create(UsersService.class)
                            .editUserInfo(mUser.getUserId(), name, mUser.getType(), phone , address, mUser.getTimeCreate())
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<User>() {
                                @Override
                                public void accept(User user) throws Exception {
                                    if (user.getUserId().equals(mUser.getUserId())){
                                        Bundle bundle = new Bundle();
                                        bundle.putBoolean("request", true);
                                        setFragmentResult(RESULT_OK, bundle);
                                        ToastUtils.showShortToast(_mActivity, "信息修改成功！");
                                        EventBusActivityScope.getDefault(_mActivity).post(new AvatarEvent(true));
                                        _mActivity.onBackPressed();
                                    }
                                    else {
                                        ToastUtils.showShortToast(_mActivity, "信息修改失败，请检查后重试！");
                                    }
                                }
                            });
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftInput();
    }
}
