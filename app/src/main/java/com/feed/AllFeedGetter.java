package com.feed;

import com.FeedGetter;
import com.google.gson.Gson;
import com.model.Articlelist;
import com.model.Feed;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public class AllFeedGetter extends FeedOnlineGetter {
    @Override
    public List<Feed> filterFeed(List<Feed> input) {
        List<Feed> results = new ArrayList<>();
        for (Feed feed : input) {
            Boolean isDuplicated = isDuplicate(feed);
            if (isDuplicated) {
                duplicateCount++;
            } else {
                results.add(feed);
            }

        }
        return results;
    }


}
