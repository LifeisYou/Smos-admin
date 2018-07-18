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

    private Context mContext;

    private volatile static String serverIp;
    private volatile static String serverPort;
    private static SharedPreferences mSharedPreferences;

    private SharedPreferencesUtils(){}

    private static class SharedPreferencesHolder {
        private static SharedPreferencesUtils instance = new SharedPreferencesUtils();
    }

    public static SharedPreferencesUtils getInstance(){
        return SharedPreferencesHolder.instance;
    }

    public void setContext(Context context){
        this.mContext = context;
        getSharedPreferences();
    }

    private void getSharedPreferences(){
        if (mContext != null){
            mSharedPreferences = mContext.getSharedPreferences(PACK, Context.MODE_PRIVATE);
        }
    }

    public String getServerIp() {
        //填写ip信息
        serverIp = mSharedPreferences.getString(IP_KEY, "");
        if ("".equals(serverIp)) {
            serverIp = "192.168.1.120";
        }
        return serverIp;
    }

    public String getServerPort() {
        serverPort = mSharedPreferences.getString(PORT_KEY, "");
        if ("".equals(serverPort)) {
            serverPort = "8081";
        }
        return serverPort;
    }

    public boolean setServer(String ip, String port){
        try {
            return mSharedPreferences.edit().putString(IP_KEY, ip).commit() &&
                    mSharedPreferences.edit().putString(PORT_KEY, port).commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
//        finally {
//            serverPort = mSharedPreferences.getString(PORT_KEY, null);
//            serverIp = mSharedPreferences.getString(IP_KEY, null);
//        }
    }

    public String getBaseUrl(){
        if (serverIp == null || serverPort == null) {
            serverIp = getServerIp();
            serverPort = getServerPort();
        }
        return "http://"+serverIp+":"+serverPort+"/"+PROJECT_NAME+"/api/";
    }



    public boolean setLoginUsername(String username) {
        try {
            mSharedPreferences.edit().putString(LOGIN_USERNAME, username).apply();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getLoginUsername() {
        return mSharedPreferences.getString(LOGIN_USERNAME, "");
    }

    public boolean setLoginPassword(String password) {
        try {
            mSharedPreferences.edit().putString(LOGIN_PASSWORD, password).apply();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getLoginPassword() {
        return mSharedPreferences.getString(LOGIN_PASSWORD, "");
    }


    public boolean setAlarmSubscribeStatus(String username, boolean subscribeStatus) {
        try {
            mSharedPreferences.edit().putBoolean(username + "_alarm",subscribeStatus).apply();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean getAlarmSubscribeStatus(String username) {
        return mSharedPreferences.getBoolean(username + "_alarm", false);
    }
}
