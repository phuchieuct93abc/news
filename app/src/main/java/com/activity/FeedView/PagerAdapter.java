package com.activity.FeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feed.Feed;
import com.services.CategoryService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;
class PagerAdapter extends FragmentStatePagerAdapter {

    Feed item;
    List<Feed> listFeed;
    int count = 0;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setData(List<Feed> data){

        listFeed = data;
        count = data.size();
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
