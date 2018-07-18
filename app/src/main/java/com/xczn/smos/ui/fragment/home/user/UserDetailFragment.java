package com.xczn.smos.ui.fragment.home.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.User;
import com.xczn.smos.entity.UsersPinyin;
import com.xczn.smos.request.UsersService;
import com.xczn.smos.utils.HttpMethods;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/5/4.
 */
public class UserDetailFragment extends BaseBackFragment{

    private static final String USER = "user";
    private UsersPinyin mUser;
    private String phoneNum = "";
    private TextView tvUsername, tvName, tvPhone, tvAddress, tvTimeCreate, tvType;

    public static UserDetailFragment newInstance(UsersPinyin usersPinyin){
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER,usersPinyin);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(USER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(mUser.getName());
        initToolbarNav(toolbar);

        tvUsername = view.findViewById(R.id.user_message_username);
        tvName = view.findViewById(R.id.user_message_name);
        tvPhone = view.findViewById(R.id.user_message_phone);
        tvAddress = view.findViewById(R.id.user_message_address);
        tvTimeCreate = view.findViewById(R.id.user_message_time_create);
        tvType = view.findViewById(R.id.user_message_type);

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhone(phoneNum);
            }
        });

    }
    private void initData() {
        Observer<User> observer= new Observer<User>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(User user) {
                tvUsername.setText(user.getUserId());
                tvName.setText(user.getName());
                tvPhone.setText(user.getPhone());
                tvAddress.setText(user.getAddress());
                tvTimeCreate.setText(user.getTimeCreate());
                if ("user".equals(user.getType())) {
                    tvType.setText("运维人员");
                } else if ("admin".equals(user.getType())) {
                    tvType.setText("管理人员");
                } else {
                    tvType.setText("");
                }
                phoneNum = user.getPhone();
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

        UsersService service = HttpMethods.getRetrofit().create(UsersService.class);
        service.getUserInfo(mUser.getUserId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer);
    }

    private void dialPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + phoneNum);
        intent.setData(uri);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
