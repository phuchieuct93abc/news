package com.services;

import com.feed.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedContentService_JSON {
    public static String LINK_FEED_CONTENT = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={ID}";
    public static Feed getFeedContentFromFeed(Feed feed){
        try {
            String id=feed.getId();
            String link_request = LINK_FEED_CONTENT.replace("{ID}",id);
            String responseCategory = CategoryService_JSON.readUrl(link_request);
            JSONObject jObject = new JSONObject(responseCategory);
            String contentHTML = jObject.getJSONObject("article").getString("Body");
            feed.setContentHTML(contentHTML);
        } catch (JSONException e) {
            e.printStackTrace();
            feed.setContentHTML("Cannot get content");
        }
        return feed;

    }



}