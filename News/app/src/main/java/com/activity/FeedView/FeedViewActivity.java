package com.activity.FeedView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    @Extra("linkCategory")
    String linkCategory;
    PagerAdapter pagerAdapter;
    @ViewById
    ViewPager pager;
    @ViewById
    RelativeLayout viewSwipe;
    List<String> listFeedLink = new ArrayList<>();
    int page = 1;
    @Pref
    Config_ config;

    int indexOfFragment;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("index", indexOfFragment);
        setResult(2, intent);
        finish();
        super.onBackPressed();

    }

    @Background
    void runBackground() {
        runUI();
    }


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
        //getMoreDateFromPageFromEnd();
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
                indexOfFragment = arg0;
                if (arg0 >= CategoryService_JSON.getListFeed().size() - 2) {
                    loadMoreData();
                }
                setIsReadForFeed(arg0);


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

    private void setIsReadForFeed(int arg0) {
        Feed currentFeed = CategoryService_JSON.getListFeed().get(arg0);
        SharedPreferences sharedPreferences = Splash_.getContext().getSharedPreferences(Feed.isReadPreferences, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(currentFeed.getId(), true).apply();
    }

    @UiThread
    void setSelectedPage(int index) {
        pager.setCurrentItem(index);
    }

    @AfterInject
    void run() {

        runBackground();
        setIsReadForFeed(0);
    }

    @AfterViews
    void afterView() {
        setToolbar();

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
/*        getSupportActionBar().setIcon(R.drawable.ic_launcher_2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_feed_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.textSize:
                showTextSizeDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showTextSizeDialog() {

    }
}
