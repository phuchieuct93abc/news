package com.activity.FeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.feed.Feed;
import com.services.CategoryService_JSON;

import java.util.List;

class PagerAdapter extends FragmentStatePagerAdapter {

    Feed item;
    List<String> listLink;
    List<Feed> listFeed;
    int count = 0;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
        listFeed = CategoryService_JSON.getListFeed();
        count = listFeed.size();
    }


    public void loadMoredata(Context context) {
        listFeed = CategoryService_JSON.getListFeedAndLoadMore(context);
        count = listFeed.size();
        this.notifyDataSetChanged();
    }

    public void setListLink(List<String> categoryLink) {
        this.listLink = categoryLink;
    }


    public void setItem(Feed item) {
        this.item = item;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FeedViewFragment_();
        ((FeedViewFragment) fragment).setFeed(listFeed.get(i));
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFeed.get(position).getSourceName();
    }
}
