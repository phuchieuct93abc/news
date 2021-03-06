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


    @Override
    public Feed getFeedById(Feed feed) throws Exception {
        Log.d("get feed online", feed.getCategory() + "");

        Integer id = feed.getContentID();
        String link_request = this.source.getDetail().replace("{ID}", id + "");

        String responseCategory = httpService.readUrl(link_request);


        JSONObject jObject = new JSONObject(responseCategory);

        String contentHTML = jObject.getJSONObject("article").getString("Body");
        feed.setContentHTML(contentHTML);

        Feed.save(feed);
        return feed;
    }


}
