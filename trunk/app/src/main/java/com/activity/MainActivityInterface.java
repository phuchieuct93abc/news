package com.activity;

import android.view.View;

import com.model.Feed;

public interface MainActivityInterface {
    void onCategorySelected(String category);

    void onSelectFeed(Feed feed, View view);

    void onBackFeedList(Feed index);

    void feedOnView(Feed feed);

}
