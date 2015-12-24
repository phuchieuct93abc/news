package com.activity;

import android.view.View;

import com.feed.Feed;

public interface MainActivityInterface {
    void onCategorySelected(String category);

    void onSelectFeed(Feed feed, View view);

    void onBackFeedList(int index);

    void feedOnView(Feed feed);

}
