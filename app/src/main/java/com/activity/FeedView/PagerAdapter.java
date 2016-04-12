package com.activity.FeedView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.model.Feed;

import java.util.ArrayList;
import java.util.List;

class PagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> registeredFragments = new ArrayList<>();

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
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.add( fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(object);
        super.destroyItem(container, position, object);
    }

    public List<Fragment> getRegisteredFragment() {
        return registeredFragments;
    }


}
