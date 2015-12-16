package com.activity;

import com.feed.Feed;

/**
 * Created by ACER on 12/8/2015.
 */
public interface MainActivityInterface {
    public void onCategorySelected(String category);
    public void onSelectFeed(Feed feed);
    public void onBackFeedList(int index);
    public void feedOnView(Feed feed);

}
