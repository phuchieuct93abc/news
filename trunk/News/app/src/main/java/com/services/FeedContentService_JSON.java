package com.services;

import com.feed.Feed;

import org.json.JSONObject;

public class FeedContentService_JSON {
    public static String LINK_FEED_CONTENT = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={ID}";

    public static Feed getFeedContentFromFeed(Feed feed) {
        try {
            String id = feed.getId();
            String link_request = LINK_FEED_CONTENT.replace("{ID}", id);
            String responseCategory = CategoryService_JSON.readUrl(link_request);
            JSONObject jObject = new JSONObject(responseCategory);
            String contentHTML = jObject.getJSONObject("article").getString("Body");
            feed.setContentHTML(contentHTML);
        } catch (Exception e) {
            e.printStackTrace();
            feed.setContentHTML("Cannot get content");
        }
        return feed;

    }


}
