package com.services;

import android.util.Log;

import com.interfaces.FeedContentGetterAbstract;
import com.model.Feed;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class FeedContentOfflineGetter extends FeedContentGetterAbstract {




    @Override
    public Feed getFeedById(Feed feed) throws Exception {
        Log.d("get feed offline", feed.getContentID() + "");
        String contentId = String.valueOf(feed.getContentID());

        List<Feed> feedsOnMemory = Feed.find(Feed.class, "content_iD = ?", contentId);
        Feed feedOnMemory = feedsOnMemory.size() > 0 ? feedsOnMemory.get(0) : null;

        if (feedOnMemory == null && this.nextGetter != null) {
            return this.nextGetter.getFeedById(feed);
        }
        return feedOnMemory;
    }


}
