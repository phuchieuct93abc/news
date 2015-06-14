package com.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.activity.Splash;
import com.feed.Feed;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONObject;
@EBean
public class FeedContentService_JSON {
    public static String LINK_FEED_CONTENT = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={ID}";
    @RootContext
    Context context;
    static String sharedPreferencesCaterogy = "CATEROGY_CACHE";
    static String sharedPreferencesReadFeed = "READ_FEED_CACHE";

    public Feed getFeedContentFromFeed(Feed feed) throws Exception {
        String id = feed.getId();
        String link_request = LINK_FEED_CONTENT.replace("{ID}", id);
        String responseCategory;
        responseCategory = getContentFromCache(id, link_request, context);
        JSONObject jObject = new JSONObject(responseCategory);
        String contentHTML = jObject.getJSONObject("article").getString("Body");
        feed.setContentHTML(contentHTML);


        return feed;
    }

    private String getContentFromCache(String id, String link_request, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("CONTENT_FEED", Context.MODE_PRIVATE);

        String responseCategory;
        if (prefs.getString(id, null) == null) {
            responseCategory = CategoryService_JSON.readUrl(link_request, context);
            prefs.edit().putString(id, responseCategory).apply();
        } else {
            responseCategory = prefs.getString(id, null);
        }
        return responseCategory;
    }


    public void setRead(String feedLink) {
        SharedPreferences prefs = context.getSharedPreferences(
                FeedContentService_JSON.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(feedLink, true).commit();


    }

    public void clearCache() {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    FeedContentService_JSON.sharedPreferencesCaterogy, Context.MODE_PRIVATE);
            prefs.edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
