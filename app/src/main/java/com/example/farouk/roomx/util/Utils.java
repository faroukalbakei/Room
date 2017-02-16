package com.example.farouk.roomx.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;

import com.example.farouk.roomx.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.net.InetAddress;
import java.util.Calendar;
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
        Locale locale2 = new Locale(language_code,"EG");
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.setLayoutDirection(new Locale(language_code,"EG"));
        config2.locale = locale2;
        context.getResources().updateConfiguration(
                config2, context.getResources().getDisplayMetrics());
    }



}
