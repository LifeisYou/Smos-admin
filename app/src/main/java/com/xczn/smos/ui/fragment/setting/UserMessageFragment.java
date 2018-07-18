package com.xczn.smos.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.entity.User;
import com.xczn.smos.event.AvatarEvent;
import com.xczn.smos.request.UsersService;
import com.xczn.smos.utils.GlideImageLoader2;
import com.xczn.smos.utils.GlideUtils;
import com.xczn.smos.utils.HttpMethods;
import com.xczn.smos.utils.ImageUploadUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by zhangxiao
 * Date on 2018/5/10.
 */
public class UserMessageFragment extends BaseBackFragment implements View.OnClickListener{

    private static final String TAG = UserMessageFragment.class.getName();
    private static final String USER_ID = "user_id";
    private static final int IMAGE_PICKER = 5;
    private static final int REQUEST = 8;

    private TextView tvUsername, tvName, tvPhone, tvAddress, tvTimeCreate, tvType;
    private CircleImageView ivAvatar;

    private String userId;
    private User mUser;

    public static UserMessageFragment newInstance(String userId) {
        UserMessageFragment fragment = new UserMessageFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString(USER_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_message, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("个人信息");
        initToolbarNav(mToolbar);

        tvUsername = view.findViewById(R.id.user_message_username);
        tvName = view.findViewById(R.id.user_message_name);
        tvPhone = view.findViewById(R.id.user_message_phone);
        tvAddress = view.findViewById(R.id.user_message_address);
        tvTimeCreate = view.findViewById(R.id.user_message_time_create);
        tvType = view.findViewById(R.id.user_message_type);

        ivAvatar = view.findViewById(R.id.user_message_avatar);
        ivAvatar.setOnClickListener(this);
        Button btnEditUser = view.findViewById(R.id.btn_edit_user);
        btnEditUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.user_message_avatar:
                initAvatar();
                break;
            case R.id.btn_edit_user:
                startForResult(EditUserFragment.newInstance(mUser), REQUEST);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void initData() {
        HttpMethods.getRetrofit().create(UsersService.class)
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                               @Override
                               public void accept(User user) {
                                   mUser = user;
                                   tvUsername.setText(user.getUserId());
                                   tvName.setText(user.getName());
                                   tvPhone.setText(user.getPhone());
                                   tvAddress.setText(user.getAddress());
                                   tvTimeCreate.setText(user.getTimeCreate());
                                   switch (user.getType()) {
                                       case "user":
                                           tvType.setText("运维人员");
                                           break;
                                       case "admin":
                                           tvType.setText("管理人员");
                                           break;
                                       default:
                                           tvType.setText("");
                                           break;
                                   }
                                   GlideUtils.loadImageNoCache(_mActivity,
                                           SharedPreferencesUtils.getInstance().getBaseUrl() + "avatars?id=" + user.getUserId() + ".jpg"
                                   ,ivAvatar);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.d(TAG, "get user info is failed " + throwable.getMessage() );
                            }
                        });
    }
    private void initAvatar() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader2());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        //imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Glide.with(_mActivity).load(Uri.fromFile(new File(images.get(0).path))).into(ivAvatar);
                Uri uri = Uri.parse("file://" + images.get(0).path);
                uploadImage(uri);
            } else {
                Toast.makeText(_mActivity, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            initData();
            EventBusActivityScope.getDefault(_mActivity).post(new AvatarEvent(true));
        }
    }

    private void uploadImage(Uri uri){
        final Map<String, Bitmap> example = new HashMap<>();
        ContentResolver cr = _mActivity.getContentResolver();
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            String imageName = mUser.getUserId() + ".jpg";
            example.put(imageName, bitmap);
        } catch (Exception e){
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(ImageUploadUtils.uploadFile(handler, example, 1)){
                    System.out.println("success");
                } else {
                    System.out.println("failed");
                }
            }
        });
        thread.start();
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 11:
                    ToastUtils.showShortToast(_mActivity, "头像上传成功！");
                    break;
                case 12:
                    ToastUtils.showShortToast(_mActivity, "头像上传失败，请重试！");
                    break;
                default:
                    break;
            }
        }
    };

    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

}
