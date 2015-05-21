package com.activity.FeedView;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.config.Config_;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService_JSON;
import com.services.FeedService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.view_swipe)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class FeedViewActivity extends ActionBarActivity {

    @Extra("selectedId")
    Feed selectedId;
    PagerAdapter pagerAdapter;
    @ViewById
    ViewPager pager;
    @ViewById
    RelativeLayout viewSwipe;
    List<String> listFeedLink = new ArrayList<>();
    int page = 1;
    @Pref
    Config_ config;

    int currentIndexOfFeed;

    int indexOfFragment;


    @Background
    public void loadMoreData() {
        try {
            page++;
            pagerAdapter.loadMoredata();
            updateAdapter();
        } catch (Exception e) {
            updateAdapter();
            e.printStackTrace();

        }
    }


    @UiThread
    public void updateAdapter() {
        pagerAdapter.notifyDataSetChanged();
    }

    @UiThread
    void runUI() {
        pagerAdapter = new PagerAdapter(
                getSupportFragmentManager());
        pagerAdapter.setContext(this);
        pagerAdapter.setItem(selectedId);
        pagerAdapter.setListLink(listFeedLink);
        pagerAdapter.setTextSize(config.textSize().get());
        pager.setAdapter(pagerAdapter);

        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentIndexOfFeed = arg0;
                updateAdapter();
                try {
                    indexOfFragment = arg0;
                    if (arg0 >= CategoryService_JSON.getListFeed().size() - 2) {
                        loadMoreData();
                    }
                    CategoryService_JSON.getListFeed().get(arg0).setIsRead();
                    updateAdapter();
                } catch (Exception e) {
                    updateAdapter();
                    e.printStackTrace();
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                updateAdapter();

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        pager.setOnPageChangeListener(onPageChangeListener);
        setSelectedPage(CategoryService_JSON.getIndexInCaterogyById(selectedId));
        FeedService.setRead(selectedId.getId());
    }

    @UiThread
    void setSelectedPage(int index) {
        pager.setCurrentItem(index);
    }

    @AfterInject
    void run() {
        runUI();
        int index = CategoryService_JSON.getIndexInCaterogyById(selectedId);
        currentIndexOfFeed = index;
        CategoryService_JSON.getListFeed().get(index).setIsRead();
    }

    public void openSource(View v) {
        Feed feed = CategoryService_JSON.getListFeed().get(currentIndexOfFeed);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feed.getContentUrl()));
        startActivity(i);

    }

}
