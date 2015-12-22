package com.activity.fragment_activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.activity.ListFeedView.SimpleAnimationAdapter;
import com.activity.MainActivityInterface;
import com.feed.Feed;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

@EFragment(R.layout.activity_main)
public class ListFeedFragment extends Fragment {

    MainActivityInterface mainActivityInterface;
    @ViewById
    CustomUltimateRecyclerview listView;
    @ViewById
    ImageView imageView;
    @Bean
    SimpleAnimationAdapter adapter;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    Context context;
    Integer scrollPosition = null;

    @Override
    public void onResume() {
        super.onResume();
        List<Feed> refreshData = categoryService.getListFeed();
        adapter.setDataList(refreshData);
        adapter.notifyDataSetChanged();

    }

    @AfterViews
    void afterView() {

        adapter.setOnClickListener(new SimpleAnimationAdapter.ViewHolder.OnClickListener() {
            @Override
            public void onClick(final Feed clickedItem, View v) {




                mainActivityInterface.onSelectFeed(clickedItem,v);



            }
        });
        background(null);
        setOnScrollListener();
    }


    public void scrollToIndex(final int position) {
        scrollPosition = position;


    }


    private void setOnScrollListener() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
//        listView.setHasFixedSize(false);

        listView.setLayoutManager(llm);
        listView.enableLoadmore();
        adapter.setCustomLoadMoreView(LayoutInflater.from(context).inflate(R.layout.custom_bottom_progressbar, null));
        UltimateRecyclerView.OnLoadMoreListener loadMoreListener = new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                loadNextPage();


            }
        };
        listView.setOnLoadMoreListener(loadMoreListener);
        //  listView.setParallaxHeader(getView().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, listView.mRecyclerView, false));
        listView.setCustomSwipeToRefresh();
        Runnable callbackAfterLoadmore = new Runnable() {
            @Override
            public void run() {
                feedService.clearCache();
                categoryService.clearCacheList();
                adapter.clear();
                background(new Runnable() {
                    @Override
                    public void run() {
                        listView.scrollVerticallyTo(0);
                    }
                });
            }
        };
        refreshingMaterial(callbackAfterLoadmore);


    }

    void refreshingString(final Runnable runnable) {
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(context);
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
                new Handler().postDelayed(runnable, 1000);


            }
        });
    }
    void refreshingMaterial(final Runnable callback) {
        MaterialHeader materialHeader = new MaterialHeader(context);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(listView.mPtrFrameLayout);
        listView.mPtrFrameLayout.autoRefresh(false);
        listView.mPtrFrameLayout.setHeaderView(materialHeader);
        listView.mPtrFrameLayout.addPtrUIHandler(materialHeader);

        listView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown( frame,  content,  header);
                return canbePullDown;            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(callback, 1000);
            }
        });
    }

    @Background
    void loadNextPage() {
        List<Feed> moreData = categoryService.getMoreFeed();
        setMoreDataList(moreData,null);

    }

    @UiThread
    void setDataList(List<Feed> rssItems) {
        adapter.setDataList(rssItems);
    }

    @UiThread
    void setMoreDataList(List<Feed> rssItems, Runnable callback) {
        adapter.setMoreDataList(rssItems);
        if (listView.getAdapter() == null) listView.setAdapter(adapter);
        listView.mPtrFrameLayout.refreshComplete();
        if(scrollPosition!=null){

            if (scrollPosition > adapter.getAdapterItemCount()) {
                loadNextPage();
            } else {
                listView.scrollVerticallyToPosition(scrollPosition);
                scrollPosition=null;

            }

        }

        if(callback!=null)callback.run();
    }

    @UiThread
    void run() {
        adapter.notifyDataSetChanged();
    }



    @Background
    void background(Runnable callback) {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();
            if (moreData.isEmpty()) {
                Snackbar.make(getView().findViewById(android.R.id.content), "Loading more", Snackbar.LENGTH_LONG).show();

            } else {
                setMoreDataList(moreData,callback);

            }

        } catch (Exception e) {
            Log.e("hieu",e.getMessage());
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivityInterface = (MainActivityInterface) activity;
        context = activity;
    }


}

