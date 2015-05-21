package com.activity.FeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feed.Feed;
import com.services.CategoryService_JSON;

import java.util.List;

class PagerAdapter extends FragmentStatePagerAdapter {

    Feed item;
    List<String> listLink;
    Context context;
    int textSize;
    List<Feed> listFeed = CategoryService_JSON.getListFeed();


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public void loadMoredata() {
        listFeed = CategoryService_JSON.getListFeedAndLoadMore();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }


    public void setListLink(List<String> categoryLink) {
        this.listLink = categoryLink;
    }


    public void setItem(Feed item) {
        this.item = item;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FeedViewFragment_();
        ((FeedViewFragment) fragment).setFeed(listFeed.get(i));
        return fragment;
    }

    @Override
    public int getCount() {
        return listFeed.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFeed.get(position).getSourceName();
    }
}
