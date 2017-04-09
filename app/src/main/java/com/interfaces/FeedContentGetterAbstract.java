package com.interfaces;

import com.model.Feed;
import com.model.Source.Source;

public abstract class FeedContentGetterAbstract {
    protected Source source;
    protected FeedContentGetterAbstract nextGetter;

    public abstract Feed getFeedById(Feed feed) throws Exception;

    public void setNextGetter(FeedContentGetterAbstract nextGetter) {
        this.nextGetter = nextGetter;

    }

    ;

    public void setSource(Source source) {
        this.source = source;
        if (this.nextGetter != null) {

            this.nextGetter.setSource(source);
        }

    }

}
