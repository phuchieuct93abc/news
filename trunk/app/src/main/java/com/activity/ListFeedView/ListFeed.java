package com.activity.ListFeedView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.activity.FeedView.FeedViewActivity_;
import com.feed.Feed;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
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

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        List<Feed> refreshData = categoryService.getListFeed();
//        adapter.setDataList(refreshData);
//    }

    @AfterViews
    void afterView() {

        tool_bar.setTitle(categoryName);
        feedService.clearCache();
        categoryService.clearCacheList();
        adapter.setOnClickListener(new SimpleAnimationAdapter.ViewHolder.OnClickListener() {
            @Override
            public void onClick(Feed clickedItem) {
                FeedViewActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).selectedId(clickedItem).start();

            }
        });
//        adapter.setOnClickListener(new RecycleViewAdapter.PersonViewHolder.OnClickListener() {
//            @Override
//            public void onClick(Feed feed) {
//                FeedViewActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).selectedId(feed).start();
//            }
//        });
        background();
        setOnScrollListener();
    }

    private void setOnScrollListener() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        listView.setHasFixedSize(false);

        listView.setLayoutManager(llm);
        listView.enableLoadmore();

        listView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                loadNextPage();

            }
        });
        listView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, listView.mRecyclerView, false));
        //   ultimateRecyclerView.setNormalHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));

//        listView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
//            @Override
//            public void onParallaxScroll(float percentage, float offset, View parallax) {
//                Drawable c = tool_bar.getBackground();
//                c.setAlpha(Math.round(127 + percentage * 128));
//                tool_bar.setBackgroundDrawable(c);
//            }
//        });

//        listView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                feedService.clearCache();
//                categoryService.clearCacheList();
//                adapter.setDataList(new ArrayList<Feed>());
//                background();
//
//            }
//        });

//        listView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
//            @Override
//            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
//            }
//
//            @Override
//            public void onDownMotionEvent() {
//            }
//
//            @Override
//            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
//                if (observableScrollState == ObservableScrollState.UP) {
//                    listView.hideToolbar(tool_bar, listView, getScreenHeight());
//                } else if (observableScrollState == ObservableScrollState.DOWN) {
//
//                    listView.showToolbar(tool_bar, listView, getScreenHeight());
//                }
//            }
//        });
        listView.setCustomSwipeToRefresh();
        refreshingString();




    }
    void refreshingString() {
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);
        //   header.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("Refreshing...");
        //  header.initWithStringArray(R.array.akta);
//        listView.mPtrFrameLayout.removePtrUIHandler(new MaterialHeader(this));

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
                feedService.clearCache();
                categoryService.clearCacheList();
                adapter.clear();
                background();
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
            setMoreDataList(moreData);




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    int getScreenHeight(){
        return 10;
    }

}

