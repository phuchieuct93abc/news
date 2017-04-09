package com.services;

import android.util.Log;

import com.interfaces.FeedContentGetterInterface;
import com.model.Feed;
import com.model.Source.Source;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.json.JSONObject;

@EBean
public class FeedContentOnlineGetter implements FeedContentGetterInterface {
    Source source;

    @Bean
    HttpService httpService;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

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
        return feed;
    }
}
