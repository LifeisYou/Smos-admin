package com.xczn.smos.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangxiao
 * Date on 2018/5/7.
 */
public class SharedPreferencesUtils {

    private static final String PACK= "smos_msg";
    private static final String IP_KEY= "server_ip_key";
    private static final String PORT_KEY = "server_port_key";
    private static final String PROJECT_NAME = "secp8000";
    private static final String LOGIN_USERNAME = "login_name";
    private static final String LOGIN_PASSWORD = "login_password";

    private volatile static String serverIp;
    private volatile static String serverPort;
    private static SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        if (context != null){
            mSharedPreferences = context.getSharedPreferences(PACK, Context.MODE_PRIVATE);
        }
    }

    private static class SharedPreferencesHolder {
        private static SharedPreferencesUtils instance = new SharedPreferencesUtils();
    }

    public static SharedPreferencesUtils getInstance(){
        return SharedPreferencesHolder.instance;
    }

    public String getServerIp() {
        //填写ip信息
        serverIp = mSharedPreferences.getString(IP_KEY, "192.168.1.120");
        return serverIp;
    }

    public String getServerPort() {
        serverPort = mSharedPreferences.getString(PORT_KEY, "8081");
        return serverPort;
    }

    public boolean setServer(String ip, String port){
        return mSharedPreferences.edit().putString(IP_KEY, ip).commit() &&
                    mSharedPreferences.edit().putString(PORT_KEY, port).commit();
    }

    public String getBaseUrl(){
        if (serverIp == null || serverPort == null) {
            serverIp = getServerIp();
            serverPort = getServerPort();
        }
        return "http://"+serverIp+":"+serverPort+"/"+PROJECT_NAME+"/api/";
    }

    public void setLoginUsername(String username) {
        mSharedPreferences.edit().putString(LOGIN_USERNAME, username).apply();
    }
    public void setLoginInfo(String username, String password) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LOGIN_USERNAME, username);
        editor.putString(LOGIN_PASSWORD, password);
        editor.apply();
    }

    public String getLoginUsername() {
        return mSharedPreferences.getString(LOGIN_USERNAME, "");
    }

    public String getLoginPassword() {
        return mSharedPreferences.getString(LOGIN_PASSWORD, "");
    }

    public boolean setAlarmSubscribeStatus(String username, boolean subscribeStatus) {
        return mSharedPreferences.edit().putBoolean(username + "_alarm",subscribeStatus).commit();
    }

    public boolean getAlarmSubscribeStatus(String username) {
        return mSharedPreferences.getBoolean(username + "_alarm", false);
    }
}
