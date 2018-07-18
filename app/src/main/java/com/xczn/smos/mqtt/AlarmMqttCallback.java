package com.xczn.smos.mqtt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;
import com.xczn.smos.MainActivity;
import com.xczn.smos.R;
import com.xczn.smos.dao.Alarm;
import com.xczn.smos.dao.Alarm_;
import com.xczn.smos.entity.AlarmReceive;
import com.xczn.smos.utils.BoxStoreUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;
import com.xczn.smos.utils.TimeUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import io.objectbox.Box;

/**
 * @Author zhangxiao
 * @Date 2018/4/28 0028
 * @Comment
 */

public class AlarmMqttCallback implements MqttCallback {

    private final FragmentActivity activity;
    private static int MessageID = 0;
    private NotificationManager notificationManager = null;

    public AlarmMqttCallback(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void connectionLost(Throwable cause) {
//        Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
    }

    /**
     * 当消息推送来，调用
     * @param topic 主题
     * @param message 消息信息
     * @throws Exception 错误
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("message has arrived");
        Box<Alarm> alarmBox = BoxStoreUtils.getInstance().getBoxStore().boxFor(Alarm.class);
        String str1 = new String(message.getPayload());
        AlarmReceive alarmReceive = new Gson().fromJson(str1, AlarmReceive.class);
        System.out.println(alarmReceive.getMessage());
        Alarm alarm = new Alarm();
        alarm.alarmId = alarmReceive.getAlarm_id();
        alarm.equipment = alarmReceive.getEquipment();
        alarm.message = alarmReceive.getMessage();
        alarm.time = TimeUtils.date2String(alarmReceive.getAlarm_time());
        alarm.level = alarmReceive.getLevel();
        alarm.status = "undo";
        alarm.username = SharedPreferencesUtils.getInstance().getLoginUsername();
        if (alarmBox.find(Alarm_.alarmId, alarm.alarmId).size() == 0) {
            System.out.println(alarm.getAlarmId());
            alarmBox.put(alarm);
            sendNotification(alarm);
            //EventBusActivityScope.getDefault(activity).post( new AlarmPostEvent(1));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void sendNotification(Alarm alarm){
        String id = "alarm_01";
        String name="Alarm";

//        NotificationManager manager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
//        Notification.Builder mBuilder = new Notification.Builder(activity.getApplicationContext())
//                .setSmallIcon(R.drawable.ic_menu_list_alarm)
//                .setTicker("新的报警")
//                .setContentTitle(alarm.equipment)
//                .setContentText(alarm.message)
//                .setOngoing(false)
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true);

//        Intent intent = new Intent(activity, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pendingIntent);
//        manager.notify(1, mBuilder.build());
        if (notificationManager == null) {
            notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Intent intent = new Intent(activity, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Bitmap largeIcon = ((BitmapDrawable) activity.getResources().getDrawable(R.drawable.soft_image)).getBitmap();

        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(activity)
                    .setSmallIcon(R.drawable.ic_menu_list_alarm)
                    .setLargeIcon(largeIcon)
                    .setTicker("新的报警")
                    .setContentTitle(alarm.equipment)
                    .setContentText(alarm.message)
                    .setOngoing(false)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .build();
        } else {
            //NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(activity)
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(activity.getApplicationContext())
                    .setSmallIcon(R.drawable.ic_menu_list_alarm)
                    .setLargeIcon(largeIcon)
                    .setTicker("新的报警")
                    .setContentTitle(alarm.equipment)
                    .setContentText(alarm.message)
                    .setOngoing(false)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

                    //.setChannel(id);//无效
            //notificationBuilder.setContentIntent(pendingIntent);
            notification = notificationBuilder.build();
        }
        notificationManager.notify(MessageID, notification);
        MessageID++;

        Alerter.create(activity)
                .setIcon(R.drawable.ic_menu_list_alarm)
                .setTitle(alarm.equipment)
                .setText(alarm.message)
                .setBackgroundColorRes(R.color.newColorAccent)
                .enableSwipeToDismiss()
                .show();
    }
}
