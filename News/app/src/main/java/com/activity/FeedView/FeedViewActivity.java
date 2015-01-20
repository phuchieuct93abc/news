package com.activity.FeedView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.activity.ListFeedView.FeedViewFragment;
import com.config.Config_;
import com.feed.Feed;
import com.phuchieu.news.R;
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

    @Extra("selectedLink")
    String link;
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

    @Background
    void runBackground() {
        List<String> abc = FeedService.getCategoryBaseOnFeed(linkCategory, link);
        linkCategory = abc.get(0);
        page = Integer.parseInt(abc.get(1));
        List<String> categoryFromPageOne = FeedService.getLinkCategoryFromPageOne(linkCategory);
        for (String item : categoryFromPageOne) {
            for (String item2 : FeedService.getListFeedLinkFromCaterogy(item)) {
                this.listFeedLink.add(item2);
            }
        }
        runUI();
    }

    @Background
    public void loadMoreData() {
        page++;
        getMoreDateFromPageFromEnd();
    }


    private void getMoreDateFromPageFromEnd() {
        getMoreDateFromPage(false);
    }

    private void getMoreDateFromPage(Boolean fromStart) {
        String nextLink = FeedService.getLinkByPageNumber(linkCategory, page);
        List<Feed> feeds = FeedService.getFeedFromUrl(nextLink);
        if (fromStart) {
            for (Feed feed : feeds) {
                String feedLink = feed.getLink();
                listFeedLink.add(0, feedLink);
            }
        }
        for (Feed feed : feeds) {
            String feedLink = feed.getLink();
            listFeedLink.add(feedLink);
        }

        pagerAdapter.setListLink(listFeedLink);
        updateAdapter();
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
        pagerAdapter.setLink(link);
        pagerAdapter.setListLink(listFeedLink);
        pagerAdapter.setTextSize(config.textSize().get());
        pager.setAdapter(pagerAdapter);

        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                FeedService.setRead(listFeedLink.get(arg0));
                if (arg0 == listFeedLink.size() - 2) {
                    loadMoreData();
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
        setSelectedPage(listFeedLink.indexOf(link));
        FeedService.setRead(link);


    }

    @UiThread
    void setSelectedPage(int index) {
        pager.setCurrentItem(index);
    }

    @AfterInject
    void run() {

        runBackground();
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
        Log.i("hieu", item.getItemId() + " " + R.id.textSize);
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
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        final TextView textView = new TextView(this);
        final int[] textSize = new int[1];
        textView.setText("Text 123");
        final LinearLayout[] layout = {new LinearLayout(this)};
        layout[0].setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout[0].setOrientation(LinearLayout.VERTICAL);
        layout[0].addView(seek);
        layout[0].addView(textView);
        seek.setMax(20);
        popDialog.setTitle("Change Text Size");
        popDialog.setView(layout[0]);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setTextSize(progress * 2);
                textSize[0] = progress *2;
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        List<Fragment> fragments = getSupportFragmentManager().getFragments();
                        for(int i=0;i<fragments.size();i++){
                            FeedViewFragment view = (FeedViewFragment) fragments.get(i);
                            view.setTextSize(textSize[0]);
                        }
                       config.edit().textSize().put(textSize[0]).apply();
                        pagerAdapter.setTextSize(textSize[0]);
                        dialog.dismiss();
                    }
                });
        popDialog.create();
        popDialog.show();
    }
}
