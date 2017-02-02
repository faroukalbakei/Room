package com.example.farouk.roomx.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.farouk.roomx.model.User;
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

    public void setUser(User user){
        Log.d("set user ",user.toString());
        Gson gson = new Gson();
        String json = gson.toJson(user);
        sharedPreferences
                .edit()
                .putString(PRE_USER, json)
                .apply();
    }

    public boolean getPreLoad(){
        return sharedPreferences.getBoolean(PRE_LOAD, false);
   }
    public User getUser(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PRE_USER, "");
        User user = gson.fromJson(json, User.class);
        Log.d("get user",user.toString());
        return user;
    }

}
