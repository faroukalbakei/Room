package com.example.farouk.roomx.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.farouk.roomx.model.LoginResponse;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.google.gson.Gson;

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

    public boolean getPreLoad(){
        return sharedPreferences.getBoolean(PRE_LOAD, false);
   }
    public LoginResponse getUser(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PRE_USER, "");
        LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);
        //Log.d("get userinfoLogin", userinfoLogin.toString());
        return loginResponse;
    }

}
