package com.feed;

import android.content.Context;

import com.FeedGetter;
import com.model.Feed;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuchieuPC on 4/9/2017.
 */
@EBean
public class UnReadGetter extends FeedOnlineGetter {

    @RootContext
    Context ctx;
    @Override
    public List<Feed> filterFeed(List<Feed> input) {
        List<Feed> results = new ArrayList<>();
        for (Feed feed : input) {
            Boolean isDuplicated = isDuplicate(feed);
            Boolean isRead = feed.isRead(ctx);
            if (isDuplicated || isRead) {
                duplicateCount++;
            } else {
                results.add(feed);
            }

        }
        return results;
    }
}
