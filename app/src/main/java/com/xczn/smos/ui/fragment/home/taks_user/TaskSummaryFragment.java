package com.xczn.smos.ui.fragment.home.taks_user;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.timmy.tdialog.TDialog;
import com.xczn.smos.R;
import com.xczn.smos.adapter.TaskSummaryImageAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.Task;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.ui.fragment.preview.PhotoViewFragment;
import com.xczn.smos.ui.view.MyGridView;
import com.xczn.smos.utils.GlideImageLoader2;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.ImageUploadUtils;
import com.xczn.smos.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 */
public class TaskSummaryFragment extends BaseBackFragment {

    public static final String TAG = TaskSummaryFragment.class.getSimpleName();
    public static final int UPLOADIMAGE = 0;
    private static final String TASK_ID = "task_id";
    public static final int TASK_SUMMARY_STATUS = 3;
    public static final int IMAGE_PICKER = 5;

    private Toolbar mToolbar;
    private TextView tvMessage, tvEquipment, tvPublisher, tvTimeDeadline;
    private EditText etMsgReceive;
    private MyGridView gvTaskPictures;
    private TDialog tdUploading;

    private String taskId;
    private Task mTask;
    private TaskSummaryImageAdapter imageAdapter;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片


    public static TaskSummaryFragment newInstance(String taskId) {
        TaskSummaryFragment fragment = new TaskSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TASK_ID, taskId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            taskId = bundle.getString(TASK_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_summary, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Observer<Task> observer = new Observer<Task>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(Task task) {
                mTask = task;
                mToolbar.setTitle(mTask.getTask().getTaskId());
                tvMessage.setText(mTask.getTask().getMessage());
                tvEquipment.setText(mTask.getTask().getEquipment());
                tvPublisher.setText(mTask.getTask().getPublisher());
                tvTimeDeadline.setText(mTask.getTask().getTimeDeadline());
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

        TaskService taskService = HttpMethods.getRetrofit().create(TaskService.class);
        taskService.getTask(taskId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        initToolbarNav(mToolbar);

        tvMessage = view.findViewById(R.id.tv_task_message);
        tvEquipment = view.findViewById(R.id.tv_task_equipment);
        tvPublisher = view.findViewById(R.id.tv_task_publisher);
        tvTimeDeadline = view.findViewById(R.id.tv_task_time_deadline);
        etMsgReceive = view.findViewById(R.id.et_task_msg_receive);
        etMsgReceive.setFocusable(false);
        gvTaskPictures = view.findViewById(R.id.gv_task_pictures);

        Button btnSummary = view.findViewById(R.id.btn_summary_task);
        ImageButton btnPicAdd = view.findViewById(R.id.btn_task_pictures_add);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader2());   //设置图片加载器
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9-selImageList.size());    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        btnPicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPic();
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput();
                tdUploading = new TDialog.Builder(getFragmentManager())
                        .setLayoutRes(R.layout.dialog_uploading)
                        .setWidth(300)
                        .setHeight(300)
                        .setCancelable(false)
                        .create()
                        .show();
                uploadImage();
            }
        });
    }

    private void selectPic() {
        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);

    }

    private void summaryTask() {
        Observer<String> observer = new Observer<String>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(String str) {
                tdUploading.dismiss();
                if ("true".equals(str)) {
                    Toast.makeText(_mActivity, "任务提交成功", Toast.LENGTH_SHORT).show();
                    _mActivity.onBackPressed();
                } else {
                    Toast.makeText(_mActivity, "任务提交失败", Toast.LENGTH_SHORT).show();
                }
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
        TaskService taskService = HttpMethods.getRetrofit().create(TaskService.class);
        taskService.summaryTask(mTask.getTask().getTaskId(), etMsgReceive.getText().toString(),
                TimeUtils.getCurrentTime(), TASK_SUMMARY_STATUS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                // Glide.with(_mActivity).load(Uri.fromFile(new File(images.get(0).path))).into(imageDemo);
                selImageList.addAll(images);
                imageAdapter = new TaskSummaryImageAdapter(_mActivity,selImageList);
//                gvTaskPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(_mActivity, ImagePreviewActivity.class);
//                        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//                        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, images);
//                        intent.putExtra(ImagePreviewActivity.ISORIGIN, false);
//                        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//                        startActivity(intent);
//                    }
//                });
                gvTaskPictures.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();

                //图片点击，显示预览图
                gvTaskPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayList<String> list = new ArrayList<>();
                        for (ImageItem item : selImageList){
                            list.add(item.path);
                        }
                        start(PhotoViewFragment.newInstance(i, list, false));

                    }
                });

                //图片长按，是否删除
                gvTaskPictures.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        return false;
                    }
                });

            } else {
                Toast.makeText(_mActivity, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(){
        final Map<String, Bitmap> example = new HashMap<>();
        ContentResolver cr = _mActivity.getContentResolver();

        for (int i = 0; i <selImageList.size(); i++){
            Uri uri = Uri.parse("file://"+selImageList.get(i).path);
            try{
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                String imageName = mTask.getTask().getTaskId() + "_" + i + ".jpg";
                example.put(imageName, bitmap);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(ImageUploadUtils.uploadFile(handler, example, UPLOADIMAGE)){
                    System.out.println("success");
                } else {
                    System.out.println("failed");
                }
            }
        });
        thread.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 11:
//                    Toast.makeText(_mActivity, "success", Toast.LENGTH_SHORT).show();
                    summaryTask();
                    break;
                case 12:
                    break;
                default:
                    break;
            }
        }
    };
}
