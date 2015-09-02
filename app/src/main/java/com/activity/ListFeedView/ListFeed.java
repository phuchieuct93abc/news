package com.activity.ListFeedView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.activity.FeedView.FeedViewActivity_;
import com.feed.Feed;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class ListFeed extends Activity {


    @ViewById
    UltimateRecyclerView listView;
    @Bean
    RecycleViewAdapter adapter;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    @ViewById
    Toolbar tool_bar;
    @Extra
    String categoryName;
    Context context = this;

    @Override
    public void onResume() {
        super.onResume();
        List<Feed> refreshData = categoryService.getListFeed();
        adapter.setDataList(refreshData);
    }

    @AfterViews
    void afterView() {

        tool_bar.setTitle(categoryName);
        feedService.clearCache();
        categoryService.clearCacheList();
        adapter.setOnClickListener(new RecycleViewAdapter.PersonViewHolder.OnClickListener() {
            @Override
            public void onClick(Feed clickedItem) {
                Intent i = new Intent(getApplicationContext(), FeedViewActivity_.class);
                i.putExtra("selectedId", clickedItem);
                startActivity(i);
            }
        });
        background();

        setOnScrollListener();
    }

    private void setOnScrollListener() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        listView.setLayoutManager(llm);
        listView.enableLoadmore();

        listView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                loadNextPage();

            }
        });




        listView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                feedService.clearCache();
                categoryService.clearCacheList();
                adapter.setDataList(new ArrayList<Feed>());
                background();

            }
        });
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        listView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (observableScrollState == ObservableScrollState.DOWN) {
                    listView.showToolbar(tool_bar, listView, height);
                } else if (observableScrollState == ObservableScrollState.UP) {
                    listView.hideToolbar(tool_bar, listView, height);
                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });

        //    listView.setItemAnimator(Type.values()[position].getAnimator());
        // listView.getItemAnimator().setAddDuration(300);
        // listView.getItemAnimator().setRemoveDuration(300);
//        listView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
//        listView.setupMoreListener(new OnMoreListener() {
//            @Override
//            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
//
//
//
//
//            }
//
//        }, 3);


    }

    @Background
    void loadNextPage() {
        List<Feed> moreData = categoryService.getMoreFeed();
        setMoreDataList(moreData);

    }

    @UiThread
    void setDataList(List<Feed> rssItems) {
        adapter.setDataList(rssItems);
    }

    @UiThread
    void setMoreDataList(List<Feed> rssItems) {

        adapter.setMoreDataList(rssItems);
        if (listView.getAdapter() == null) listView.setAdapter(adapter);

//        listView.hideMoreProgress();

    }


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

            List<Feed> moreData = categoryService.getMoreFeed();
            setMoreDataList(moreData);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

