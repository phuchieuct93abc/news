package com.builder;

import com.interfaces.FeedContentGetterInterface;
import com.services.FeedContentOnlineGetter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by phuchieuPC on 4/9/2017.
 */
@EBean
public class FeedContentGetterBuilder {
    @Bean
    FeedContentOnlineGetter feedContentOnlineGetter;

    public FeedContentGetterInterface getFeedContentGetter() {
        return feedContentOnlineGetter;


    }
}
