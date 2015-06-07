package com.services;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.annotations.EBean;

@EBean
public class FeedService {
    static Context context;
    static String sharedPreferencesCaterogy = "CATEROGY_CACHE";
    static String sharedPreferencesReadFeed = "READ_FEED_CACHE";


    public static void setRead(String feedLink) {
        SharedPreferences prefs = getContext().getSharedPreferences(
                FeedService.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(feedLink, true).commit();


    }

    public static void clearCache() {
        try {
            SharedPreferences prefs = getContext().getSharedPreferences(
                    FeedService.sharedPreferencesCaterogy, Context.MODE_PRIVATE);
            prefs.edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        FeedService.context = context;
    }


}
