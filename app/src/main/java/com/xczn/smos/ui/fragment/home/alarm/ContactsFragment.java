package com.xczn.smos.ui.fragment.home.alarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/6/6.
 */
public class ContactsFragment extends BaseBackFragment {

    private static final String TAG = ContactsFragment.class.getName();
    private static final String USER_ID = "user_id";
    private static final int RETURN_USER_CODE = 9;
    private RecyclerView rvContacts;
    private ContactsAdapter mAdapter;
    private ArrayList<UsersPinyin> usersPinyinsList = new ArrayList<>();

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        initView(view);
        initData();
        return view;
    }

    @SuppressLint("CheckResult")
    private void initData() {
        HttpMethods.getRetrofit().create(UsersService.class)
                .getUserList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UserList>>() {
                               @Override
                               public void accept(List<UserList> userLists) throws Exception {
                                   usersPinyinsList.clear();
                                   for (UserList user : userLists) {
                                       if (user.getType().equals("admin"))
                                           continue;
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
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("用户");
        initToolbarNav(mToolbar);

        rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactsAdapter(usersPinyinsList, R.layout.item_users);
        rvContacts.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
//                EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(DetailFragment.newInstance(mAdapter.getItem(position).getTitle())));
                Bundle bundle = new Bundle();
                bundle.putString(USER_ID, usersPinyinsList.get(position).getUserId());
                setFragmentResult(RESULT_OK, bundle);
                _mActivity.onBackPressed();
            }
        });

        WaveSideBar sideBar = view.findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < usersPinyinsList.size(); i++) {
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