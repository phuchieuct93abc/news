//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.feed;

import android.content.Context;
import com.services.FeedService_;

public final class FeedListAdapter_
    extends FeedListAdapter
{

    private Context context_;

    private FeedListAdapter_(Context context) {
        context_ = context;
        init_();
    }

    public static FeedListAdapter_ getInstance_(Context context) {
        return new FeedListAdapter_(context);
    }

    private void init_() {
        context = context_;
        feedService = FeedService_.getInstance_(context_);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}