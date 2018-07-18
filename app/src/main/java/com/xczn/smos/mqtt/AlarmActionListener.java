package com.xczn.smos.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @Author zhangxiao
 * @Date 2018/4/28 0028
 * @Comment
 */

public class AlarmActionListener implements IMqttActionListener{

    private final Context context;
    private final MqttAndroidClient client;

    public AlarmActionListener(Context context, MqttAndroidClient client) {
        this.context = context;
        this.client = client;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        try {
            client.subscribe("Alarm", 2);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        exception.printStackTrace();
    }
}
