package com.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.activity.Splash;
import com.feed.Feed;

import org.json.JSONObject;

public class FeedContentService_JSON {
    public static String LINK_FEED_CONTENT = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={ID}";

    public static Feed getFeedContentFromFeed(Feed feed) throws Exception {
        SharedPreferences prefs = Splash.getContext().getSharedPreferences("CONTENT_FEED", Context.MODE_PRIVATE);
        String id = feed.getId();
        String link_request = LINK_FEED_CONTENT.replace("{ID}", id);
        String responseCategory;
        responseCategory = getContentFromCache(prefs, id, link_request);
        JSONObject jObject = new JSONObject(responseCategory);
        String contentHTML = jObject.getJSONObject("article").getString("Body");
        feed.setContentHTML(contentHTML);


        return feed;
    }

    private static String getContentFromCache(SharedPreferences prefs, String id, String link_request) {
        String responseCategory;
        if (prefs.getString(id, null) == null) {
            responseCategory = CategoryService_JSON.readUrl(link_request);
            prefs.edit().putString(id, responseCategory).apply();
        } else {
            responseCategory = prefs.getString(id, null);
        }
        return responseCategory;
    }
}
