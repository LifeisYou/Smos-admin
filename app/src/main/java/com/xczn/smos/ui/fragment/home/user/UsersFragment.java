package com.xczn.smos.ui.fragment.home.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.xczn.smos.R;
import com.xczn.smos.adapter.ContactsAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.UserList;
import com.xczn.smos.entity.UsersPinyin;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.request.UsersService;
import com.xczn.smos.ui.view.EmptyLayout;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class UsersFragment extends BaseBackFragment {

    public static final String TAG = UsersFragment.class.getName();
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;
    private ContactsAdapter mAdapter;
    private EmptyLayout mEmptyLayout;

    private UsersService usersService;
    private ArrayList<UsersPinyin> usersPinyinsList = new ArrayList<>();

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Observer<List<UserList>> observer= new Observer<List<UserList>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(List<UserList> myUsers) {

                usersPinyinsList.clear();
                for (UserList user:myUsers) {
                    UsersPinyin newUser = new UsersPinyin();
                    newUser.setUserId(user.getUserId());
                    newUser.setName(user.getName());
                    newUser.setPhone(user.getPhone());
                    newUser.setType(user.getType());
                    newUser.setNamePinyin(PinyinUtils.getPingYin(user.getName()).toUpperCase());
                    newUser.setIndex(PinyinUtils.getFirstAlpha(newUser.getNamePinyin()));
                    usersPinyinsList.add(newUser);
                }

                Collections.sort(usersPinyinsList, new Comparator<UsersPinyin>() {
                    @Override
                    public int compare(UsersPinyin o1, UsersPinyin o2) {
                        return o1.getNamePinyin().compareTo(o2.getNamePinyin());
                    }
                });
                mAdapter.notifyDataSetChanged();
                if (usersPinyinsList.isEmpty()){
                    mEmptyLayout.showEmpty();
                } else {
                    mEmptyLayout.hide();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());
                mEmptyLayout.showError();
                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        };


        usersService = HttpMethods.getRetrofit().create(UsersService.class);
        usersService.getUserList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("用户");
        initToolbarNav(mToolbar);

        mEmptyLayout = view.findViewById(R.id.user_emptyLayout);
        mEmptyLayout.setLoadingMessage("正在加载中...");
        //mEmptyLayout.showLoading(R.drawable.ic_loading, "正在加载中。。。");
        mEmptyLayout.showLoading();

        rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactsAdapter(usersPinyinsList, R.layout.item_users);
        rvContacts.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
//                Toast.makeText(_mActivity,usersPinyinsList.get(position).getName(),Toast.LENGTH_SHORT).show();
                start(UserDetailFragment.newInstance(usersPinyinsList.get(position)));
                // 或者使用EventBus
//                EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(DetailFragment.newInstance(mAdapter.getItem(position).getTitle())));
            }
        });
        sideBar = view.findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<usersPinyinsList.size(); i++) {
                    if (usersPinyinsList.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
}
