package com.interfaces;

import com.model.Feed;

import java.util.List;

/**
 * Created by phuchieuPC on 4/9/2017.
 */

public interface FeedGetterInterfacer {
    public List<Feed> getFeed();
    public List<Feed> getMore();
    public List<Feed> filterFeed(List<Feed> input);


}
