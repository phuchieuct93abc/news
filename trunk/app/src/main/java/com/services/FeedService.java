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
    static String sharedPreferencesCaterogy = "CATEROGY_CACHE";
    static String sharedPreferencesReadFeed = "READ_FEED_CACHE";
    @RootContext
    Context context;
    @Bean
    HttpService httpService;

    public Feed getFeedContentFromFeed(Feed feed) throws Exception {

        Integer id = feed.getContentID();
        String link_request;
        link_request = LINK_FEED_CONTENT.replace("{ID}", id+"");
//
//        if (!feed.isCNET()) {
//
//        } else
//        {
//            link_request = feed.getBaomoiUrl();
//        }
        String responseCategory;
        responseCategory = getContentFromCache(id+"", link_request, context);
        String contentHTML;
//       if (feed.isCNET()) {
//            contentHTML = responseCategory;
//        } else
        {
            JSONObject jObject = new JSONObject(responseCategory);

            contentHTML = jObject.getJSONObject("article").getString("Body");

        }
        feed.setContentHTML(contentHTML);


        return feed;
    }

    private String getContentFromCache(String id, String link_request, Context context) {


        return httpService.readUrl(link_request);
    }


    public void setRead(String feedLink) {
        SharedPreferences prefs = context.getSharedPreferences(
                FeedService.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(feedLink, true).apply();


    }

    public void clearCache() {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    FeedService.sharedPreferencesCaterogy, Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
