package com.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.model.Feed;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONObject;

@EBean
public class FeedService {
    public static String LINK_FEED_CONTENT = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={ID}";
    static String sharedPreferencesReadFeed = "READ_FEED_CACHE";
    @RootContext
    Context context;
    @Bean
    HttpService httpService;

    public Feed getFeedContentFromFeed(final Feed feed) throws Exception {

        Integer id = feed.getContentID();
        String link_request;
        link_request = LINK_FEED_CONTENT.replace("{ID}", id + "");

        String responseCategory;
        responseCategory = httpService.readUrl(link_request);
        String contentHTML;

        JSONObject jObject = new JSONObject(responseCategory);

        contentHTML = jObject.getJSONObject("article").getString("Body");


        feed.setContentHTML(contentHTML);


        return feed;
    }


    public void setRead(String feedLink) {
        SharedPreferences prefs = context.getSharedPreferences(
                FeedService.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(feedLink, true).apply();


    }


}
