package com.interfaces;

import com.model.Feed;
import com.model.Source.Source;

/**
 * Created by phuchieuPC on 4/9/2017.
 */
public interface FeedContentGetterInterface {
    public Feed getFeedById(Feed feed) throws Exception;

    public void setSource(Source source);
}
