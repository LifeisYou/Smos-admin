package com.xczn.smos.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangxiao
 * Date on 2018/5/24.
 */
public class ToastUtils {

    public static void showShortToast(Context context, CharSequence text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, CharSequence text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

}
