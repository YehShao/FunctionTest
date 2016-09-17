package com.function.bruce.functiontest.utils;

public class Log {
    private static final String TAG = "[FunctionTest]";
    // FIXME: do not show debug logs according to BuildConfig.DEBUG
    private static final boolean DEBUG = true;

    public static void i(String tag, String msg) {
        android.util.Log.i(TAG, "[" + tag + "] " + msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) android.util.Log.d(TAG, "[" + tag + "] " + msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(TAG, "[" + tag + "] " + msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        android.util.Log.w(TAG, "[" + tag + "] " + msg, tr);
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(TAG, "[" + tag + "] " + msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(TAG, "[" + tag + "] " + msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        android.util.Log.e(TAG, "[" + tag + "] " + msg, tr);
    }
}
