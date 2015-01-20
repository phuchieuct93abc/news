package com.feed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.services.FeedService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class FeedListAdapter extends BaseAdapter {

    List<Feed> feeds = new ArrayList<Feed>();

    @RootContext
    Context context;
    @Bean
    FeedService feedService;

    public void setListDataMore(List<Feed> feeds) {
        for (Feed feed : feeds) {

            this.feeds.add(feed);
        }
    }

    public void setDataFromSearch(Feed feed) {
        feeds.add(feed);

    }

    public void setListData(List<Feed> feeds) {
        clear();

        for (Feed feed : feeds) {

            this.feeds.add(feed);
        }
    }

    public void clear() {
        feeds = new ArrayList<Feed>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FeedItemView FeedItemView;
        if (convertView == null) {
            FeedItemView = FeedItemView_.build(context);
        } else {
            FeedItemView = (FeedItemView) convertView;
        }

        FeedItemView.bindDataToView(getItem(position));

        return FeedItemView;
    }

    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public Feed getItem(int position) {
        return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}