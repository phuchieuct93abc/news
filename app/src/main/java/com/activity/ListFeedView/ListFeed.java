package com.activity.ListFeedView;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.activity.FeedView.FeedViewActivity_;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class ListFeed extends Activity {


    @ViewById
    SuperListview listView;
    @Bean
    FeedListAdapter adapter;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;

    @Override
    public void onResume() {
        super.onResume();
        List<Feed> refreshData = categoryService.getListFeed();
        adapter.setDataList(refreshData);
        Log.e("hieu","init"+adapter.getCount());
    }

    @AfterViews
    void afterView() {
        feedService.clearCache();
        categoryService.clearCacheList();
        listView.setAdapter(adapter);
        background();
        setOnScrollListener();
    }

    private void setOnScrollListener() {
        listView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedService.clearCache();
                categoryService.clearCacheList();
                adapter.setDataList(new ArrayList<Feed>());
                background();
            }
        });
        listView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {


                loadNextPage();
                listView.hideMoreProgress();


            }

        }, 3);


    }

    @Background
    void loadNextPage() {
        List<Feed> moreData= categoryService.getMoreFeed();
        Log.e("hieu","load more"+moreData);
        setMoreDataList(moreData);
    }

    @UiThread
    void setDataList( List<Feed> rssItems) {
        Log.e("hieu","set data"+adapter.getCount());
        adapter.setDataList(rssItems);
    }

    @UiThread
    void setMoreDataList( List<Feed> rssItems) {
        Log.e("hieu","more data"+adapter.getCount());

        adapter.setMoreDataList(rssItems);
        Log.e("hieu","more data"+adapter.getCount());
    }
    @ItemClick
    public void listViewItemClicked(Feed clickedItem) {
        Intent i = new Intent(getApplicationContext(), FeedViewActivity_.class);
        i.putExtra("selectedId", clickedItem);
        startActivity(i);
    }

    @UiThread
    void run() {
        adapter.notifyDataSetChanged();
    }

    @Background
    void background() {
        try {
            Log.e("hieu","init 1 data"+adapter.getCount());

            List<Feed> moreData = categoryService.getMoreFeed();
            setMoreDataList(moreData);
            Log.e("hieu", "init 2 data" + adapter.getCount());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

