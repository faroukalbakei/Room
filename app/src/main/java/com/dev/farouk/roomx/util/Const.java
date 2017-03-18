package com.dev.farouk.roomx.util;

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
    public static String getMyRoom_URL = "getmyrooms";
    public static String getReservation_URL="getuserreservations";
    public static String getReservationRequest_URL="getmyreservations";
    public static String acceptreservation = "acceptreservation";


    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String BE_HOST= "";
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,};
}
