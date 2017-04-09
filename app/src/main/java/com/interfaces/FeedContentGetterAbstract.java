package com.interfaces;

import com.model.Feed;
import com.model.Source.Source;

public abstract class FeedContentGetterAbstract {
    protected Source source;

    public abstract Feed getFeedById(Feed feed) throws Exception;

    public abstract void setNextGetter(FeedContentGetterAbstract nextGetter);

    public void setSource(Source source) {
        this.source = source;
    }

}
