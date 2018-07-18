package com.xczn.smos.ui.fragment.home.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.xczn.smos.R;
import com.xczn.smos.adapter.ImageAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Task;
import com.xczn.smos.ui.fragment.preview.PhotoViewFragment;
import com.xczn.smos.ui.view.MyGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author zhangxiao
 * @Date 2018/3/5 0005
 * @Comment
 */

public class TaskDetailFragment extends BaseBackFragment {

    public static final String TAG = TaskDetailFragment.class.getSimpleName();
    private static final int REQ_MODIFY_FRAGMENT = 100;

    private static final String ARG_TITLE = "arg_title";
    static final String KEY_RESULT_TITLE = "title";

    private MyGridView gridView;
    private LinearLayout llTaskPic;

    private Task mTask;

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TITLE,task);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTask = (Task) bundle.getSerializable(ARG_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);
        return view;
    }



    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        gridView = view.findViewById(R.id.task_image_gridView);

        mToolbar.setTitle("任务详情");
        initToolbarNav(mToolbar);

        llTaskPic = view.findViewById(R.id.ll_task_pic);
        TextView tvTaskId = view.findViewById(R.id.tv_task_detail_id);
        TextView tvTaskMsg = view.findViewById(R.id.tv_task_detail_message);
        TextView tvTaskEquipment = view.findViewById(R.id.tv_task_detail_equipment);
        TextView tvTaskType = view.findViewById(R.id.tv_task_detail_type);
        TextView tvTaskReceiver = view.findViewById(R.id.tv_task_detail_receiver);
        TextView tvTaskPublisher = view.findViewById(R.id.tv_task_detail_publisher);
        TextView tvTaskDeadline = view.findViewById(R.id.tv_task_detail_deadline);
        TextView tvTaskPublishTime = view.findViewById(R.id.tv_task_detail_publish_time);

        if (mTask != null){
            tvTaskId.setText(mTask.getTask().getTaskId());
            tvTaskMsg.setText(mTask.getTask().getMessage());
            tvTaskEquipment.setText(mTask.getTask().getEquipment());
            tvTaskType.setText(mTask.getTask().getType());
            tvTaskReceiver.setText(mTask.getTask().getReceiver());
            tvTaskPublisher.setText(mTask.getTask().getPublisher());
            tvTaskDeadline.setText(mTask.getTask().getTimeDeadline());
            tvTaskPublishTime.setText(mTask.getTask().getTimePublish());
        }
    }

    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        initDelayView();
    }

    private void initDelayView() {
       // final ArrayList<ImageItem> images =new ArrayList<>();

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(_mActivity, PreviewActivity.class);
//                intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//                intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//                intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, images);
//                intent.putExtra(ImagePreviewActivity.ISORIGIN, false);
//                intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//                startActivity(intent);
//            }
//        });

        if (mTask.getTask().getStatus() == 3){
            llTaskPic.setVisibility(View.VISIBLE);
            ImageAdapter imageAdapter = new ImageAdapter(_mActivity, mTask.getPictures());
            gridView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    start(PhotoViewFragment.newInstance(i, convertListToArrayList(mTask.getPictures()), true));
                }
            });
        }
    }

    /**
     * 将未知类型list转成arraylist
     * @param orig
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> convertListToArrayList(List<T> orig) {
        //如果为null直接返回，这里也可以把size=0加上
        if (null == orig) return null;
        if (orig instanceof ArrayList) {//判断是否就是ArrayList,如果是，则强转
            return (ArrayList)orig;
        } else {
            ArrayList<T> returnValue = new ArrayList<>(orig.size());
            for (T t : orig) {
                returnValue.add(t);
            }
            //jdk1.8及以上可以使用这样的循环遍历
            //orig.forEach(t -> returnValue.add(t));
            return returnValue;
        }
    }
}
