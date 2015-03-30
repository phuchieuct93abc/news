package com.activity.FeedView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.activity.Splash_;
import com.config.Config_;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService_JSON;
import com.services.FeedService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
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
    String id;
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("hieu", e.getMessage());
        } finally {
            updateAdapter();

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
        pagerAdapter.setLink(id);
        pagerAdapter.setListLink(listFeedLink);
        pagerAdapter.setTextSize(config.textSize().get());
        Log.i("hieu", "adapter");
        pager.setAdapter(pagerAdapter);

        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // FeedService.setRead(listFeedLink.get(arg0));
                currentIndexOfFeed = arg0;
                try {
                    indexOfFragment = arg0;
                    if (arg0 >= CategoryService_JSON.getListFeed().size() - 2) {
                        loadMoreData();
                    }
                    CategoryService_JSON.getListFeed().get(arg0).setIsRead();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("hieu", e.getMessage());
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        pager.setOnPageChangeListener(onPageChangeListener);
        setSelectedPage(CategoryService_JSON.getIndexInCaterogyById(id));
        FeedService.setRead(id);
    }

    @UiThread
    void setSelectedPage(int index) {
        pager.setCurrentItem(index);
    }

    @AfterInject
    void run() {
        runUI();
        int index = CategoryService_JSON.getIndexInCaterogyById(id);
        currentIndexOfFeed =index;
        CategoryService_JSON.getListFeed().get(index).setIsRead();
    }

    public void openSource(View v){
        Feed feed = CategoryService_JSON.getListFeed().get(currentIndexOfFeed);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feed.getContentUrl()));
        startActivity(i);

    }

}
