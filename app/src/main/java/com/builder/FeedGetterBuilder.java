package com.builder;

import android.util.Log;

import com.FeedGetter;
import com.config.Config_;
import com.feed.AllFeedGetter;
import com.feed.ReadGetter;
import com.feed.UnReadGetter;
import com.services.ViewModeEnum;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean
public class FeedGetterBuilder {
    @Pref
    private Config_ config;

    public FeedGetter getFeedGetter() {

        ViewModeEnum viewModeEnum = ViewModeEnum.getByCode(config.viewMode().get());
        switch (viewModeEnum) {
            case ALL_FEED:
                return new AllFeedGetter();
            case READ_FEED:
                return new ReadGetter();
            case UNREAD_FEED:
                return new UnReadGetter();
        }
        return new AllFeedGetter();
    }
}
