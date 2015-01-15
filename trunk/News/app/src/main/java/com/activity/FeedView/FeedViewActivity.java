package com.activity.FeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.activity.ListFeedView.FeedViewFragment;
import com.activity.ListFeedView.FeedViewFragment_;
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

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.view_swipe)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class FeedViewActivity extends ActionBarActivity {

    @Extra("selectedLink")
    String link;
    @Extra("linkCategory")
    String linkCategory;

    PagerAdapter mDemoCollectionPagerAdapter;
    @ViewById
    ViewPager pager;
    List<String> listFeedLink = new ArrayList<String>();
    int page = 1;

    @Background
    void runBackground() {
        List<String> abc = FeedService.getCategoryBaseOnFeed(linkCategory, link);
        Log.i("hieu", abc.get(0) + " " + abc.get(1));
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
        } else {

        }
        for (Feed feed : feeds) {
            String feedLink = feed.getLink();
            listFeedLink.add(feedLink);
        }

        mDemoCollectionPagerAdapter.setListLink(listFeedLink);
        updateAdapter();
    }

    @UiThread
    public void updateAdapter() {
        mDemoCollectionPagerAdapter.notifyDataSetChanged();

    }

    @UiThread
    void runUI() {
        mDemoCollectionPagerAdapter = new PagerAdapter(
                getSupportFragmentManager());
        mDemoCollectionPagerAdapter.setContext(this);
        mDemoCollectionPagerAdapter.setLink(link);
        mDemoCollectionPagerAdapter.setListLink(listFeedLink);
        pager.setAdapter(mDemoCollectionPagerAdapter);

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
        inflater.inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class PagerAdapter extends FragmentStatePagerAdapter {

    FeedService feedService = new FeedService();
    String link;
    List<String> listLink;
    Context context;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addMoreListLink(String link) {
        listLink.add(link);
    }

    public List<String> getListLink() {
        return listLink;
    }

    public void setListLink(List<String> categoryLink) {
        this.listLink = categoryLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FeedViewFragment_();
        ((FeedViewFragment) fragment).setContext(this.context);
        ((FeedViewFragment) fragment).setLink(getListLink().get(i));
        return fragment;
    }

    @Override
    public int getCount() {
        return listLink.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
    /*	String feedLink = getListLink().get(position);
		// String title = FeedService.getFeedContent(feedLink).getTitle();
*/
        String title = "NEWS " + position;
        return title;
    }
}
