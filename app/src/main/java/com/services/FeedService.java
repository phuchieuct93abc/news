package com.services;

import android.content.Context;
import android.widget.ImageView;

import com.builder.FeedContentGetterBuilder;
import com.interfaces.FeedContentGetterAbstract;
import com.model.Feed;
import com.model.Source.Source;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

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
    @Bean
    FeedContentGetterBuilder feedContentGetterBuilder;
    FeedContentGetterAbstract feedContentGetter;

    @AfterInject
    void init() {
        picasso = Picasso.with(context.getApplicationContext());
        feedContentGetter = feedContentGetterBuilder.getFeedContentGetter();

    }

    public Feed getFeedContentFromFeed(final Feed feed) throws Exception {

        return feedContentGetter.getFeedById(feed);
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

            String imageUrl = "http://www.google.com/s2/favicons?domain=" + getSource(urlSource);


            picasso.load(imageUrl).into(imageView);



    }


    public void setFeedContentLink(Source source) {
        feedContentGetter.setSource(source);
    }
}
