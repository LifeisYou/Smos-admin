package com.xczn.smos.ui.fragment.home.alarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.request.TaskService;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxiao
 * Date on 2018/5/18.
 */
public class TaskPublishFragment  extends BaseBackFragment implements View.OnClickListener{

    private static final String EQUIP ="equip";
    private static final String MSG = "msg";
    private static final String ALARM_ID = "alarmId";
    private static final String TAG = "Sample";
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private static final int RETURN_USER_CODE = 9;
    private static final int TASK_PUBLISH_STATUS = 0;
    private static final String USER_ID = "user_id";

    private String equipment;
    private String message;
    private String alarmId;

    private TextView tvPublishTaskEquipment ,tvPublishTaskUser, tvPublishTaskType, tvPublishTaskDeadline;
    private EditText etPublishTaskMessage;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    public static TaskPublishFragment newInstance(String taskEquipment, String taskMessage, String alarmId) {
        TaskPublishFragment fragment = new TaskPublishFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EQUIP, taskEquipment);
        bundle.putString(MSG, taskMessage);
        bundle.putString(ALARM_ID, alarmId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            equipment = bundle.getString(EQUIP);
            message = bundle.getString(MSG);
            alarmId = bundle.getString(ALARM_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_publish, container, false);
        initView(view);
        initDatePicker();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("任务发布");
        initToolbarNav(toolbar);

        tvPublishTaskEquipment = view.findViewById(R.id.tv_publish_task_equipment);

        tvPublishTaskDeadline = view.findViewById(R.id.tv_publish_task_time);
        tvPublishTaskDeadline.setOnClickListener(this);
        tvPublishTaskUser = view.findViewById(R.id.tv_publish_task_user);
        tvPublishTaskUser.setOnClickListener(this);
        tvPublishTaskType = view.findViewById(R.id.tv_publish_task_type);
        tvPublishTaskType.setOnClickListener(this);
        etPublishTaskMessage = view.findViewById(R.id.et_publish_task_message);

        tvPublishTaskEquipment.setText(equipment);
        etPublishTaskMessage.setText(message);

        Button btnPublishTask = view.findViewById(R.id.btn_alarm_task_publish);
        btnPublishTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_publish_task_time:
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(new Date(System.currentTimeMillis()));
                dateTimeFragment.show(getFragmentManager(), TAG_DATETIME_FRAGMENT);
                break;
            case R.id.btn_alarm_task_publish:
                publishTask();
                break;
            case R.id.tv_publish_task_type:
                        selectTaskType();
                break;
            case R.id.tv_publish_task_user:
                startForResult(ContactsFragment.newInstance(), RETURN_USER_CODE);
                break;
            default:
                    break;
        }
    }

    private void selectTaskType(){
        new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_task_type)    //设置弹窗展示的xml布局
                .setScreenWidthAspect(_mActivity, 0.6f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER_VERTICAL)     //设置弹窗展示位置
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .setCancelable(true)    //弹窗是否可以取消
                .addOnClickListener(R.id.tv_important_type_task, R.id.tv_internet_type_task,R.id.tv_equipment_type_task )   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        String taskType = "";
                        switch (view.getId()) {
                            case R.id.tv_internet_type_task:
                                taskType = "网络错误";
                                break;
                            case R.id.tv_important_type_task:
                                taskType = "紧急任务";
                                break;
                            case R.id.tv_equipment_type_task:
                                taskType = "设备故障";
                                break;
                        }
                        tvPublishTaskType.setText(taskType);
                        tDialog.dismiss();
                    }
                })
                .create()   //创建TDialog
                .show();    //展示
    }

    //任务发布
    @SuppressLint("CheckResult")
    private void publishTask() {

        String userId = SharedPreferencesUtils.getInstance().getLoginUsername();
        String receiverId = tvPublishTaskUser.getText().toString();
        String equipment = tvPublishTaskEquipment.getText().toString();
        String taskType = tvPublishTaskType.getText().toString();
        //生成任务ID号
        String taskId = getTaskId();
        String taskMsg = etPublishTaskMessage.getText().toString();
        String timeDeadline = tvPublishTaskDeadline.getText().toString();

        HttpMethods.getRetrofit().create(TaskService.class)
                .publishTask(taskId, taskType, userId, receiverId, equipment, taskMsg,
                TASK_PUBLISH_STATUS, timeDeadline, TimeUtils.getCurrentTime(), alarmId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) {
                                   if ("true".equals(s)) {
                                       Toast.makeText(_mActivity, "任务下发成功！", Toast.LENGTH_SHORT).show();
                                       _mActivity.onBackPressed();
                                   } else if ("alarm".equals(s)) {
                                       Toast.makeText(_mActivity, "该报警其他管理员已经处理，请选择直接处理！", Toast.LENGTH_SHORT).show();
                                   } else {
                                       Toast.makeText(_mActivity, "任务下发失败！", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        Log.d(TAG, "onFragmentResult: "  + requestCode);
        if (requestCode == RETURN_USER_CODE && resultCode == RESULT_OK) {
            tvPublishTaskUser.setText(data.getString(USER_ID));
        }
    }

    private void initDatePicker() {
        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.datetime_dialog),
                    getString(R.string.datetime_ok),
                    getString(R.string.datetime_cancel),
                    getString(R.string.datetime_clean) // Optional
            );
        }
        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                tvPublishTaskDeadline.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                tvPublishTaskDeadline.setText("请选择");
            }
        });
    }

    private String getTaskId(){
        //生成一个随机的三位数，与时间戳组合成taskId
        int id = new Random().nextInt(900) + 100;
        return "T" + System.currentTimeMillis() + "A" + String.valueOf(id);
    }
}
