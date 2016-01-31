package com.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.activity.FeedView.FeedViewActivity_;
import com.activity.fragment_activity.CaterogyFragment_;
import com.activity.fragment_activity.ListFeedFragment;
import com.activity.fragment_activity.ListFeedFragment_;
import com.model.Feed;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main2)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    final static String CATEGORY_FRAGMENT = "CaterogyFragment";
    final static String LIST_FEED_FRAGMENT = "ListFeedFragment_";
    final static String FEED_VIEW_FRAGMENT = "FeedViewFragment";
    @ViewById
    Toolbar toolbar;
    Menu menu;
    Integer feedId;
    Feed feedOnView;

    @AfterViews
    public void init() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new CaterogyFragment_(), CATEGORY_FRAGMENT).commit();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCategorySelected(String category) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

        ListFeedFragment sharedElementFragment1 = new ListFeedFragment_();

        fragmentTransaction.replace(R.id.fragment, sharedElementFragment1, LIST_FEED_FRAGMENT).addToBackStack(null).commit();

        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setVisibilityForAllItem(boolean visibility) {
        this.menu.getItem(0).setVisible(visibility);
        this.menu.getItem(1).setVisible(visibility);
        this.menu.getItem(2).setVisible(visibility);
    }

    @Override
    public void onSelectFeed(Feed feed, View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("feedId", feed.getContentID());
        getSupportActionBar().setTitle(feed.getSourceName());
        setVisibilityForAllItem(true);
        startFeedViewFragment(bundle, v);
    }

    private void startFeedViewFragment(Bundle bundle, View v) {
        FeedViewActivity_ sharedElementFragment2 = new FeedViewActivity_();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
        sharedElementFragment2.setArguments(bundle);
        fragmentTransaction
                .replace(R.id.fragment, sharedElementFragment2, FEED_VIEW_FRAGMENT)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onBackFeedList(Feed feedBefore) {

        feedId = feedBefore.getContentID();
    }

    @Override
    public void feedOnView(Feed feed) {
        feedOnView = feed;

    }

    private void onBack() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FeedViewActivity_ feedViewActivity_ = (FeedViewActivity_) fragmentManager.findFragmentByTag(FEED_VIEW_FRAGMENT);
            CaterogyFragment_ caterogyFragment_ = (CaterogyFragment_) fragmentManager.findFragmentByTag(CATEGORY_FRAGMENT);

            ListFeedFragment_ listFeedFragment_ = (ListFeedFragment_) fragmentManager.findFragmentByTag(LIST_FEED_FRAGMENT);

            if (feedViewActivity_ != null && feedViewActivity_.isVisible()) {

            } else if (caterogyFragment_ != null && caterogyFragment_.isVisible()) {

                setVisibilityForAllItem(false);

            } else if (listFeedFragment_ != null && listFeedFragment_.isVisible()) {
                setVisibilityForAllItem(false);
                listFeedFragment_.scrollToIndex(feedId);

            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            onBack();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void share() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, feedOnView.getContentUrl());
        startActivity(intent);
    }

    private void openSource() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feedOnView.getContentUrl()));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.setting:
                share();
                break;
            case R.id.web:
                openSource();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_view, menu);
        this.menu = menu;
        setVisibilityForAllItem(false);
        return true;
    }
}
