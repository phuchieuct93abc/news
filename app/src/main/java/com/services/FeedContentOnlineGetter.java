package com.services;

import android.util.Log;

import com.interfaces.FeedContentGetterAbstract;
import com.model.Feed;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.json.JSONObject;

@EBean
public class FeedContentOnlineGetter extends FeedContentGetterAbstract {

    @Bean
    HttpService httpService;
    FeedContentGetterAbstract nextSetter;



    @Override
    public Feed getFeedById(Feed feed) throws Exception {
        Integer id = feed.getContentID();
        String link_request;
        link_request = this.source.getDetail().replace("{ID}", id + "");

        String responseCategory;
        Log.d("aa", link_request);
        responseCategory = httpService.readUrl(link_request);
        String contentHTML;

        JSONObject jObject = new JSONObject(responseCategory);

        contentHTML = jObject.getJSONObject("article").getString("Body");
        feed.setContentHTML(contentHTML);
        Feed.save(feed);
        return feed;
    }

    @Override
    public void setNextGetter(FeedContentGetterAbstract nextGetter) {
        this.nextSetter = nextGetter;
    }
}
