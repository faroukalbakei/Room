package com.example.farouk.roomx.util;

import android.Manifest;

/**
 * Created by AbAbdullah on 24/11/2016.
 */

public class Const {

    public static String BASE_URL = "http://roomxapi.com/gaithapi/public/api/";
    public static String PLACE_ID;
    public static String PLACE_PID;

    public static String getExplore_URL = "shownewestrooms";
    public static String getFavList_URL = "showwishlist";

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final int REQUEST_LOCATION= 2;
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,};
}
