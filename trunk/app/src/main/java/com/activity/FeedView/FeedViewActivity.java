package com.activity.FeedView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.config.SharePreference;
import com.feed.Feed;
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
import org.androidannotations.annotations.WindowFeature;

import java.util.List;

@EActivity(R.layout.view_swipe)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class FeedViewActivity extends AppCompatActivity {

    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    @Extra("selectedId")
    Feed selectedId;
    PagerAdapter pagerAdapter;
    @ViewById
    ViewPager pager;
    @ViewById
    RelativeLayout viewSwipe;
    int currentIndexOfFeed;
    int indexOfFragment;
    final Context context = this;


    @Background
    public void loadMoreData() {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();
            setMoreDataList(moreData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @UiThread
    public void setDataList(List<Feed> feeds)

    {
        pagerAdapter.setData(feeds);
    }

    @UiThread
    public void setMoreDataList(List<Feed> feeds)

    {
        pagerAdapter.setMoreData(feeds);
    }

    @UiThread
    void runUI() {

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.setData(categoryService.getListFeed());
        pagerAdapter.setItem(selectedId);
        pager.setAdapter(pagerAdapter);

        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentIndexOfFeed = arg0;
                try {
                    categoryService.getListFeed().get(arg0).setIsRead(context);
                    indexOfFragment = arg0;
                    if (arg0 == pagerAdapter.getCount() - 1) {
                        loadMoreData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
        setSelectedPage(categoryService.getIndexInCaterogyById(selectedId));
        feedService.setRead(selectedId.getId());
    }

    @UiThread
    void setSelectedPage(int index) {
        pager.setCurrentItem(index);
    }

    @AfterViews
    void run() {

        try {
            runUI();
            int index = categoryService.getIndexInCaterogyById(selectedId);
            currentIndexOfFeed = index;
            categoryService.getListFeed().get(index).setIsRead(context);
            setDataList(categoryService.getListFeed());

            setBackgroundColor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBackgroundColor() {
        Boolean darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);
        if (darkBackground) {
            viewSwipe.setBackgroundColor(Color.parseColor("#23282A"));

        }
    }

    public void openSource(View v) {
        Feed feed = categoryService.getListFeed().get(currentIndexOfFeed);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feed.getContentUrl()));
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("previousItem", currentIndexOfFeed);

        setResult(111, intent);
        finish();
    }
}
