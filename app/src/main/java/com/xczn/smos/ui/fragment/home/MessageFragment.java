package com.xczn.smos.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.xczn.smos.R;
import com.xczn.smos.adapter.ImageAdapter;
import com.xczn.smos.adapter.ImagePickerAdapter;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.utils.GlideImageLoader2;

import java.util.ArrayList;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class MessageFragment extends BaseBackFragment {

    public static final int IMAGE_PICKER = 5;
    private GridView gridView;
    private ImageView imageDemo;
    private ImagePickerAdapter adapter;
    private ImageAdapter imageAdapter;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("消息");
        initToolbarNav(mToolbar);

        gridView = view.findViewById(R.id.image_gridView);

        Button upLoad = view.findViewById(R.id.upLoad_btn);
        upLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_mActivity, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader2());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        gridView = view.findViewById(R.id.image_gridView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
               // Glide.with(_mActivity).load(Uri.fromFile(new File(images.get(0).path))).into(imageDemo);
                selImageList.addAll(images);
                //imageAdapter = new ImageAdapter(_mActivity,selImageList);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(_mActivity, ImagePreviewActivity.class);
                        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, images);
                        intent.putExtra(ImagePreviewActivity.ISORIGIN, false);
                        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                        startActivity(intent);
                    }
                });
                gridView.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(_mActivity, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
