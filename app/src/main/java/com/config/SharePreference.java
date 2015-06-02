package com.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lphieu on 5/27/2015.
 */
public class SharePreference {
    public static String DARK_BACKGROUND = "DARK_BACKGROUND";
    private static String GLOBAL_PREF = "com.hieu.news";
    Context context;
    SharedPreferences settings;

    public SharePreference(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(GLOBAL_PREF, 0);
    }

    public String getStringValue(String value) {

        return settings.getString(value, "");
    }

    public int getIntValue(String value) {
        return settings.getInt(value, 0);
    }

    public Boolean getBooleanValue(String value) {
        return settings.getBoolean(value, false);
    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setBoleanValue(String key, Boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
