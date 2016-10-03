package com.services;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class CacheProvider {
    private static String PREF_FEED_NAME = "CACHE_FEED";
    private static String PREF_CATEGORY_NAME = "CACHE_CATEGORY";
    @RootContext
    Context context;
    SharedPreferences cacheFeed;
    SharedPreferences cacheCategory;

    private boolean isCategory(String url) {
        return url.indexOf("http://dataprovider.touch.baomoi.com/json/articlelist.aspx") != -1;
    }

    @AfterInject
    void init() {
        cacheFeed = context.getSharedPreferences(PREF_FEED_NAME, Context.MODE_PRIVATE);
        cacheCategory = context.getSharedPreferences(PREF_CATEGORY_NAME, Context.MODE_PRIVATE);
    }

    public String get(String url) {
        if (isCategory(url)) {
            // return cacheCategory.getString(url, null);
            return null;
        } else {
            return cacheFeed.getString(url, null);

        }

    }

    public void put(String url, String data) {
        if (isCategory(url)) {
            cacheCategory.edit().putString(url, data).apply();
        } else {
            cacheFeed.edit().putString(url, data).apply();

        }
    }

    public void clearCache() {
        cacheCategory.edit().clear().apply();
    }
}
