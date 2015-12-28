package com.activity.FeedView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.model.Feed;

import java.util.ArrayList;
import java.util.List;

class PagerAdapter extends FragmentStatePagerAdapter {

    Feed item;
    List<Feed> listFeed = new ArrayList<>();
    int count = 0;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Feed> data) {
        listFeed = new ArrayList<>();
        listFeed.addAll(data);
        count = listFeed.size();
        this.notifyDataSetChanged();
    }

    public void setMoreData(List<Feed> data) {

        listFeed.addAll(data);
        count = listFeed.size();
        this.notifyDataSetChanged();
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
