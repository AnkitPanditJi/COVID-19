package com.blavasgps.covidcheckup.commons.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created By Ravi Kant on 26/11/2019
 */

public class AppUtils {

    public static final int REQUEST_LOCATION_PERMISSIONS = 1014;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String KEY_USER_CURRENT_LOCATION = "user_current_location";

    public static void toast(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean getIsGpsOn(Context ctx) {
        //        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return mPrefs.getBoolean("gps", false);
    }

    public static void setIsGpsOn(Context ctx, boolean value) {
        //        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putBoolean("gps", value);
        mPrefsEditor.commit();
    }

    public static String getCurrentLocation(Context cx) {
        SharedPreferences preferences = cx.getSharedPreferences(KEY_USER_CURRENT_LOCATION, Context.MODE_PRIVATE);
        return preferences.getString(KEY_USER_CURRENT_LOCATION, "-1");
    }


    public static void setCurrentLocation(Context cx, String value) {
        SharedPreferences preferences = cx.getSharedPreferences(KEY_USER_CURRENT_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_CURRENT_LOCATION, value);
        editor.commit();
    }

    public static class Language{
        public static String ENGLISH = "en";
        public static String HINDI = "hi";
    }


    public static void setAppLanguage(Activity context, String value)
    {
        Locale locale = new Locale(value);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(config,
        context.getBaseContext().getResources().getDisplayMetrics());
        context.recreate();
    }

    public static final String setMapType = "maptype";

    public static ProgressDialog getProgressDialog(Context context,String title, String message)
    {
        ProgressDialog progressDialog = null;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    @SuppressLint("MissingPermission")
    public static void makePhoneCall(Context context, String mobileNumber)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mobileNumber));
        context.startActivity(intent);
    }

    public static void togglePushNotification(Context context)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Push Notification !");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Enable", (dialog, id) -> dialog.cancel());
        builder1.setNegativeButton("Disable", (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static int getMapType(Context context)
    {
        return ((SharedPreferences) PreferenceManager.getDefaultSharedPreferences(context)).getInt(setMapType, 1);
    }

    public static void setMapType(Context applicationContext,
                                      int stringconvert) {
        SharedPreferences msharedPreferences = (SharedPreferences)PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor edit = msharedPreferences.edit();
        edit.putInt(setMapType, stringconvert);
        edit.commit();

    }

}
