package com.blavasgps.covidcheckup.commons.log;

import android.util.Log;

public class L {
    private static final String TAG = "BGPS-LOG";
    private static final boolean IS_LOG_ENABLE = true;
    public static void i(String msg) {
        if(IS_LOG_ENABLE)
        Log.i(TAG, msg);
    }
    public static void e(String msg) {
        if(IS_LOG_ENABLE)
        Log.e(TAG, msg);
    }
    public static void d(String msg) {
        if(IS_LOG_ENABLE)
        Log.d(TAG, msg);
    }
    public static void w(String msg) {
        if(IS_LOG_ENABLE)
        Log.w(TAG, msg);
    }

}
