package com.dev.farouk.roomx.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.dev.farouk.roomx.model.LoginResponse;
import com.dev.farouk.roomx.model.UserResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Prefs {

    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefsName";
    private static final String PRE_USER = "user";
    private static final String PRE_TOKEN = "token";
    private static final String PRE_USER_TYPE = "user_type";
    private static Prefs instance;
    private final SharedPreferences sharedPreferences;

    public Prefs(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs with(Context context) {

        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public void setPreLoad(boolean totalTime) {

        sharedPreferences
                .edit()
                .putBoolean(PRE_LOAD, totalTime)
                .apply();
    }


    public void setUser(LoginResponse loginResponse){
        Log.d("set loginResponse ", loginResponse.toString());
        Gson gson = new Gson();
        String json = gson.toJson(loginResponse);
        sharedPreferences
                .edit()
                .putString(PRE_USER, json)
                .apply();
    }

    public LoginResponse getUser(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PRE_USER, "");
        LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);
        //Log.d("get userinfoLogin", userinfoLogin.toString());
        return loginResponse;
    }

    public void setUserInfo(UserResult loginResponse){
        Log.d("setUserInfo ", loginResponse.toString());
        Gson gson ;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String json = gson.toJson(loginResponse);
        sharedPreferences
                .edit()
                .putString(PRE_LOAD, json)
                .apply();
    }

    public UserResult getUserInfo(){
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String json = sharedPreferences.getString(PRE_LOAD, "");
        UserResult loginResponse = gson.fromJson(json, UserResult.class);
        //Log.d("get userinfoLogin", userinfoLogin.toString());
        return loginResponse;
    }
}

