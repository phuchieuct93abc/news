package com.activity.ListFeedView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.activity.FeedView.FeedViewActivity_;
import com.feed.Feed;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

@EActivity(R.layout.activity_main)
public class ListFeed extends Activity {


    @ViewById
    CustomUltimateRecyclerview listView;
    @Bean
    SimpleAnimationAdapter adapter;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    @ViewById
    Toolbar tool_bar;
    @ViewById
    LinearLayout content;
    @Extra
    String categoryName;
    Context context = this;
    public final static int REQUEST_CODE = 11;

    @Override
    public void onResume() {
        super.onResume();
        List<Feed> refreshData = categoryService.getListFeed();
        adapter.setDataList(refreshData);
        adapter.notifyDataSetChanged();

    }

    @AfterViews
    void afterView() {

        tool_bar.setTitle(categoryName);
        feedService.clearCache();
        categoryService.clearCacheList();
        adapter.setOnClickListener(new SimpleAnimationAdapter.ViewHolder.OnClickListener() {
            @Override
            public void onClick(final Feed clickedItem, View v) {
                Intent i = new Intent(context, FeedViewActivity_.class);
                i.putExtra("selectedId", clickedItem);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
        background();
        setOnScrollListener();
    }

    @OnActivityResult(REQUEST_CODE)
    void onResult(int resultCode, Intent data) {
        try {
            int position = data.getIntExtra("previousItem", 0);

            listView.scrollVerticallyToPosition(position);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void setOnScrollListener() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        listView.setHasFixedSize(false);

        listView.setLayoutManager(llm);
        listView.enableLoadmore();
        UltimateRecyclerView.OnLoadMoreListener loadMoreListener = new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                loadNextPage();

            }
        };
        listView.setOnLoadMoreListener(loadMoreListener);
        listView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, listView.mRecyclerView, false));
        listView.setCustomSwipeToRefresh();
        refreshingString();


    }

    void refreshingString() {
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.initWithString("Refreshing");
        listView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        listView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        listView.mPtrFrameLayout.autoRefresh(false);
        listView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        feedService.clearCache();
                        categoryService.clearCacheList();
                        adapter.clear();
                        background();
                    }
                }, 1000);


            }
        });
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
        listView.mPtrFrameLayout.refreshComplete();
    }


    @UiThread
    void run() {
        adapter.notifyDataSetChanged();
    }

    @Background
    void background() {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();
            if (moreData.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Loading more", Snackbar.LENGTH_LONG).show();

            } else {
                setMoreDataList(moreData);

            }
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "Can not connect to network", Snackbar.LENGTH_LONG).show();

        }

    }
}

