package com.xczn.smos.ui.fragment.home.equiip;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xczn.smos.R;
import com.xczn.smos.adapter.EquipSearchResultAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Equipment1;
import com.xczn.smos.event.EquipDrawEvent;
import com.xczn.smos.event.EquipSearchEvent;
import com.xczn.smos.listener.OnItemClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/6/28.
 */
public class EquipSearchFragment extends BaseBackFragment {

    private static final String TAG = EquipSearchFragment.class.getSimpleName();
    private EditText etEquipSearch;
    private Button btnEquipSearch;
    private RecyclerView rvEquipSearchResult;

    private EquipSearchResultAdapter adapter;
    private ArrayList<Equipment1> mEquipment1List;
    private List<Equipment1> searchList = new ArrayList<>();

    public static EquipSearchFragment newInstance(ArrayList<Equipment1> list) {
        EquipSearchFragment fragment = new EquipSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mEquipment1List = getArguments().getParcelableArrayList("list");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equip_search, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        initSearch();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        initToolbarNav(toolbar);

        etEquipSearch = view.findViewById(R.id.et_equip_search);
        btnEquipSearch = view.findViewById(R.id.btn_equip_search);
        rvEquipSearchResult = view.findViewById(R.id.rv_equip_search_result);

        btnEquipSearch.setBackgroundResource(R.drawable.ic_search);
        adapter = new EquipSearchResultAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        rvEquipSearchResult.setLayoutManager(manager);
        rvEquipSearchResult.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));

        rvEquipSearchResult.setAdapter(adapter);
        etEquipSearch.setHint("请输入...");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                EventBusActivityScope.getDefault(_mActivity).post(new EquipDrawEvent(searchList.get(position)));
                _mActivity.onBackPressed();
            }
        });
    }

    private void initSearch() {
        etEquipSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = etEquipSearch.getText().toString();
                if (data.equals("")) {
                    btnEquipSearch.setBackgroundResource(R.drawable.ic_search);
                } else {
                    btnEquipSearch.setBackgroundResource(R.drawable.ic_equip_clean);
                    EventBusActivityScope.getDefault(_mActivity).post(new EquipSearchEvent(data));
                }
            }
        });

        btnEquipSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEquipSearch.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        hideSoftInput();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateEquipList(EquipSearchEvent event) {
        searchList.clear();
        String data = event.getEquipName();
        Log.d(TAG, "updateEquipList: " + data);
        for (Equipment1 equipment1 : mEquipment1List) {
            if (equipment1.getEquipname().contains(data)) {
                searchList.add(equipment1);
            }
        }
        Log.d(TAG, "updateEquipList: " + searchList.size());
        adapter.setDatas(searchList);
        adapter.notifyDataSetChanged();
    }
}
