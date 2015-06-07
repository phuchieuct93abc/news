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
import com.services.CategoryService_JSON;
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

    @Override
    public void onResume() {
        super.onResume();
        List<Feed> refreshData = CategoryService_JSON.getListFeed();
        adapter.setListDataMore(refreshData);
        updateAdapter();
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();

    }

    @AfterViews
    void afterView() {
        FeedService.clearCache();
        CategoryService_JSON.clearCacheList();
        background();
        setOnScrollListener();
    }

    private void setOnScrollListener() {

        listView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FeedService.clearCache();
                CategoryService_JSON.clearCacheList();
                background();
            }
        });
        listView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                try {
                    loadNextPage();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("hieu", e.getMessage());
                }
            }

        }, 1);


    }

    @Background
    void loadNextPage() {
        List<Feed> rssItems = new ArrayList<>();
        try {
            rssItems = CategoryService_JSON.getListFeedAndLoadMore(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.setListDataMore(rssItems);

            updateList();
        }

    }

    @UiThread
    void updateList() {
        adapter.notifyDataSetChanged();
        listView.hideMoreProgress();
    }

    @ItemClick
    public void listViewItemClicked(Feed clickedItem) {
        Intent i = new Intent(getApplicationContext(), FeedViewActivity_.class);
        i.putExtra("selectedId", clickedItem);
        startActivity(i);
    }

    @UiThread
    void run() {
        listView.setAdapter(adapter);
    }

    @Background
    void background() {
        try {

            List<Feed> rssItems = CategoryService_JSON.getListFeedAndLoadMore(getApplicationContext());
            adapter.setListData(rssItems);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

