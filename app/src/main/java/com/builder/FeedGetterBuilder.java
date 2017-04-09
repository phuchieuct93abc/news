package com.builder;

import android.util.Log;

import com.FeedGetter;
import com.config.Config_;
import com.feed.AllFeedGetter;
import com.feed.ReadGetter;
import com.feed.UnReadGetter;
import com.services.ViewModeEnum;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;



@EBean
public class FeedGetterBuilder {
    @Pref
    Config_ config;

    @Bean
    AllFeedGetter allFeedGetter;

    @Bean
     UnReadGetter unReadGetter;
    @Bean
    ReadGetter readGetter;
    public FeedGetter getFeedGetter() {

        ViewModeEnum viewModeEnum = ViewModeEnum.getByCode(config.viewMode().get());
        switch (viewModeEnum) {
            case ALL_FEED:
                return allFeedGetter;
            case READ_FEED:
                return  readGetter;
            case UNREAD_FEED:
                return unReadGetter;
        }
        return new AllFeedGetter();
    }
}
