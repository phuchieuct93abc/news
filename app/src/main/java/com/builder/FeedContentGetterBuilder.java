package com.builder;

import com.interfaces.FeedContentGetterAbstract;
import com.services.FeedContentOfflineGetter;
import com.services.FeedContentOnlineGetter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by phuchieuPC on 4/9/2017.
 */
@EBean
public class FeedContentGetterBuilder {
    @Bean
    FeedContentOnlineGetter feedContentOnlineGetter;
    @Bean
    FeedContentOfflineGetter feedContentOfflineGetter;

    @AfterInject
    public void initialize() {
        feedContentOfflineGetter.setNextGetter(feedContentOnlineGetter);

    }

    public FeedContentGetterAbstract getFeedContentGetter() {
        return feedContentOfflineGetter;


    }
}
