package com.activity.fragment_activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.activity.ListFeedView.SimpleAnimationAdapter;
import com.activity.ListFeedView.ViewHolder;
import com.activity.MainActivityInterface;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.CacheProvider;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterInject;
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
    @Bean
    CacheProvider cacheProvider;

    @AfterInject
    void afterInject() {
        cacheProvider.clearCache();
        categoryService.clearCacheList();
        initData(new Runnable() {
            @Override
            public void run() {
                listView.scrollVerticallyTo(0);
            }
        });

    }

    @AfterViews
    void afterView() {

        adapter.setOnClickListener(new ViewHolder.OnClickListener() {
            @Override
            public void onClick(final Feed clickedItem, View v) {
                mainActivityInterface.onSelectFeed(clickedItem, v);
            }
        });
        updateData(null);
        setOnScrollListener();
        setRefreshListener();
    }


    public void scrollToIndex(final Integer feedId) {
        scrollPosition = categoryService.getIndexInCaterogyById(feedId);

        if (scrollPosition != null) {
            adapter.setDataList(categoryService.getListFeed());
            adapter.notifyDataSetChanged();

            listView.scrollVerticallyToPosition(scrollPosition);
            scrollPosition = null;


        }
    }


    void setOnScrollListener() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        listView.setHasFixedSize(false);

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

        listView.setCustomSwipeToRefresh();


    }

    void setRefreshListener() {
        Runnable callbackAfterLoadmore = new Runnable() {
            @Override
            public void run() {
                cacheProvider.clearCache();

                categoryService.clearCacheList();
                adapter.clear();
                initData(new Runnable() {
                    @Override
                    public void run() {
                        listView.scrollVerticallyTo(0);
                    }
                });
            }
        };
        refreshingMaterial(callbackAfterLoadmore);
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(callback, 1000);
            }
        });
    }

    @Background
    void loadNextPage() {
        while (true) {
            try {

                List<Feed> moreData = categoryService.getMoreFeed();
                setMoreDataList(moreData, null);
                return;


            } catch (Exception e) {
                continue;
            }
        }

    }

    @UiThread
    void setDataList(List<Feed> rssItems) {
        adapter.setDataList(rssItems);
        if (listView.getAdapter() == null) listView.setAdapter(adapter);
        listView.mPtrFrameLayout.refreshComplete();
    }

    @UiThread
    void setMoreDataList(List<Feed> rssItems, Runnable callback) {
        if (!rssItems.isEmpty()) {
            adapter.setMoreDataList(rssItems);
            if (listView.getAdapter() == null) listView.setAdapter(adapter);
            listView.mPtrFrameLayout.refreshComplete();
            if (callback != null) callback.run();

        }

    }

    @UiThread
    void run() {
        adapter.notifyDataSetChanged();
    }


    @Background
    void initData(Runnable callback) {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();

            setMoreDataList(moreData, callback);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Background
    void updateData(Runnable callback) {
        try {
            List<Feed> moreData = categoryService.getListFeed();

            setDataList(moreData);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivityInterface = (MainActivityInterface) activity;
        context = activity;
    }


}

