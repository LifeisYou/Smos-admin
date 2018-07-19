package com.xczn.smos.mqtt;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xczn.smos.MainActivity;

/**
 * @Author zhangxiao
 * @Date 2018/7/19 0019
 * @Comment
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "NotificationClickReceiver";

    public static final String TYPE = "type";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            //处理点击事件
//            context.startActivity(new Intent(context, MainActivity.class));
        }

        if (action.equals("notification_cancelled")) {
            //处理滑动清除和点击删除事件
        }

    }
}
