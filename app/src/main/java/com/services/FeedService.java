package com.services;

import android.content.Context;
import android.widget.ImageView;

import com.model.Feed;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

@EBean(scope = EBean.Scope.Singleton)
public class FeedService {
    private String feedContentLink;
    @RootContext
    Context context;
    @Bean
    HttpService httpService;
    Picasso picasso;

    @AfterInject
    void init() {
        picasso = Picasso.with(context.getApplicationContext());

    }

    public Feed getFeedContentFromFeed(final Feed feed) throws Exception {

        Integer id = feed.getContentID();
        String link_request;
        link_request = this.feedContentLink.replace("{ID}", id + "");

        String responseCategory;
        responseCategory = httpService.readUrl(link_request);
        String contentHTML;

        JSONObject jObject = new JSONObject(responseCategory);

        contentHTML = jObject.getJSONObject("article").getString("Body");
        feed.setContentHTML(contentHTML);
        return feed;
    }

    public String getSource(String urlSource) {
        URL url = null;
        try {
            url = new URL(urlSource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
        return url.getHost();

    }

    public void getIconOfUrl(String urlSource, final ImageView imageView) {
        try {
            URL url = new URL(urlSource);
            String imageUrl = "http://www.google.com/s2/favicons?domain=" + getSource(urlSource);


            picasso.load(imageUrl).into(imageView);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void setFeedContentLink(String feedContentLink) {
        this.feedContentLink = feedContentLink;
    }
}
