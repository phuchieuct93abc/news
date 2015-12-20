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

import com.activity.FeedView.FeedViewActivity_;
import com.activity.fragment_activity.CaterogyFragment_;
import com.activity.fragment_activity.ListFeedFragment_;
import com.feed.Feed;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main2)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    @ViewById
    Toolbar toolbar;

    Menu menu;
    int feedIndex;
    Feed feedOnView;
    final static String  CATEGORY_FRAGMENT = "CaterogyFragment";
    final static String  LIST_FEED_FRAGMENT = "ListFeedFragment_";
    final static String  FEED_VIEW_FRAGMENT = "FeedViewFragment";


    @AfterViews
    public void init() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new CaterogyFragment_(),CATEGORY_FRAGMENT).commit();
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //icon back arrow
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }


    @Override
    public void onCategorySelected(String category) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragment, new ListFeedFragment_(),LIST_FEED_FRAGMENT).addToBackStack(null).commit();

        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onSelectFeed(Feed feed) {
        Bundle bundle = new Bundle();
        bundle.putString("feedId", feed.getId());
        FeedViewActivity_ feedViewActivity = new FeedViewActivity_();
        feedViewActivity.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right ,R.animator.slide_out_left);
        fragmentTransaction.replace(R.id.fragment, feedViewActivity,FEED_VIEW_FRAGMENT).addToBackStack(null).commit();

        getSupportActionBar().setTitle(feed.getSourceName());
        this.menu.getItem(0).setVisible(true);
        this.menu.getItem(1).setVisible(true);
        this.menu.getItem(2).setVisible(true);
    }

    @Override
    public void onBackFeedList(int index) {
        feedIndex = index;
    }

    @Override
    public void feedOnView(Feed feed) {
        feedOnView = feed;

    }

    private void onBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FeedViewActivity_ feedViewActivity_ = (FeedViewActivity_) fragmentManager.findFragmentByTag(FEED_VIEW_FRAGMENT);
        CaterogyFragment_ caterogyFragment_ = (CaterogyFragment_) fragmentManager.findFragmentByTag(CATEGORY_FRAGMENT);;
        ListFeedFragment_ listFeedFragment_ = (ListFeedFragment_) fragmentManager.findFragmentByTag(LIST_FEED_FRAGMENT);;
        if (feedViewActivity_ != null && feedViewActivity_.isVisible()) {
            //VISIBLE! =)
        } else if (caterogyFragment_ != null && caterogyFragment_.isVisible()) {

            this.menu.getItem(0).setVisible(false);
            this.menu.getItem(1).setVisible(false);
            this.menu.getItem(2).setVisible(false);
            //NOT VISIBLE =(
        } else if (listFeedFragment_ != null && listFeedFragment_.isVisible()) {
            this.menu.getItem(0).setVisible(false);
            this.menu.getItem(1).setVisible(false);
            this.menu.getItem(2).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            onBack();
            try {
                ListFeedFragment_ fragment = (ListFeedFragment_) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragment.scrollToIndex(feedIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        Log.d("hieu", item.getItemId() + "");
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.setting:
                Log.d("hieu", "setting");
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
        this.menu.getItem(0).setVisible(false);
        this.menu.getItem(1).setVisible(false);
        this.menu.getItem(2).setVisible(false);
        return true;
    }
}
