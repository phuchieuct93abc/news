package com.services;

import com.interfaces.FeedContentGetterAbstract;
import com.model.Feed;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class FeedContentOfflineGetter extends FeedContentGetterAbstract {


    @Bean
    HttpService httpService;
    FeedContentGetterAbstract nextSetter;


    @Override
    public Feed getFeedById(Feed feed) throws Exception {
        Feed feedOnMemory = Feed.findById(Feed.class, feed.getId());
        if ((feedOnMemory == null || feedOnMemory.getContentHTML() == null) && this.nextSetter != null) {
            return this.nextSetter.getFeedById(feed);
        }
        return feedOnMemory;
    }

    @Override
    public void setNextGetter(FeedContentGetterAbstract nextGetter) {
        this.nextSetter = nextGetter;
    }
}
