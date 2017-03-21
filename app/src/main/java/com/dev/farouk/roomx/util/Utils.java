package com.dev.farouk.roomx.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev Abir on 2/6/2017.
 */
public class Utils {

/*    public static boolean isInternetAvailable() {
*//*        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }*//*
        return true;
    }*/

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean isInternetAvailable(Context context) {
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

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void snakebar(String message,View view){
        Snackbar snackbar =  Snackbar.make(view, message,
                Snackbar.LENGTH_LONG).setDuration(Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();

        TextView tv= (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);

        tv.setMaxLines(5);

        snackbar.show();
    }

    public static String replaceNull(String input) {
        return input == "null" ? "" : input;
    }

}
