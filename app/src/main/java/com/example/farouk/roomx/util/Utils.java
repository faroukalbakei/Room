package com.example.farouk.roomx.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;

import java.net.InetAddress;
import java.util.Locale;

/**
 * Created by Dev Abir on 2/6/2017.
 */
public class Utils {

    public static boolean isInternetAvailable() {
/*        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }*/
        return true;
    }

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean checkConnection(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static void changeLang(Context context, String language_code) {
        Resources res2 = context.getResources();
        DisplayMetrics dm2 = res2.getDisplayMetrics();
        android.content.res.Configuration conf2 = res2.getConfiguration();
        conf2.setLayoutDirection(new Locale(language_code));
        conf2.locale = new Locale(language_code);
        res2.updateConfiguration(conf2, dm2);
    }



}
