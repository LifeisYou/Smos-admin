package com.xczn.smos.mqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * @Author zhangxiao
 * @Date 2018/4/28 0028
 * @Comment
 */

public class ConnectionModel {

    private String clientId = "MqttTest";
    private String serverHostName = "192.168.1.120";
    private int serverPort = 1883;
    private boolean cleanSession = true;
    private String username = "user";
    private String password = "user";
    private boolean tlsConnection = false;
    private int lwtQos = 2;
    private boolean lwtRetain =  false;
    private MqttAndroidClient client = null;

    public ConnectionModel(String clientId, int serverPort) {
        this.clientId = clientId;
        this.serverPort = serverPort;
    }

    public static  MqttConnectOptions getOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        options.setUserName("user");
        options.setPassword("user".toCharArray());
        return options;
    }

    public static  MqttConnectOptions getOptionsWithCleanSession(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        options.setUserName("user");
        options.setPassword("user".toCharArray());
        return options;
    }

    public MqttAndroidClient getClient() {
        return client;
    }

    public void setClient(MqttAndroidClient client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTlsConnection() {
        return tlsConnection;
    }

    public void setTlsConnection(boolean tlsConnection) {
        this.tlsConnection = tlsConnection;
    }

    public int getLwtQos() {
        return lwtQos;
    }

    public void setLwtQos(int lwtQos) {
        this.lwtQos = lwtQos;
    }

    public boolean isLwtRetain() {
        return lwtRetain;
    }

    public void setLwtRetain(boolean lwtRetain) {
        this.lwtRetain = lwtRetain;
    }


}
