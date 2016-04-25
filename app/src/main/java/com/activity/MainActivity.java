package com.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.activity.FeedView.FeedViewActivity_;
import com.activity.Fragments.DisplaySettingBottomSheet;
import com.activity.fragment_activity.CaterogyFragment_;
import com.activity.fragment_activity.ListFeedFragment;
import com.activity.fragment_activity.ListFeedFragment_;
import com.config.Config_;
import com.model.Feed;
import com.phuchieu.news.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main2)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    final static String CATEGORY_FRAGMENT = "CaterogyFragment";
    final static String LIST_FEED_FRAGMENT = "ListFeedFragment_";
    final static String FEED_VIEW_FRAGMENT = "FeedViewFragment";
    @ViewById
    Toolbar toolbar;
    @ViewById
    Switch switchDarkMode;
    @ViewById
    AppBarLayout appBarLayout;
    @ViewById
    AppBarLayout appBar;
    @ViewById
    DiscreteSeekBar seekTextsize;
    @Pref
    Config_ config;

    Menu menu;
    Integer feedId;
    Feed feedOnView;
    String category;
    Runnable changeColor;
    Runnable changeTextSize;
    DisplaySettingBottomSheet displaySettingBottomSheet;



    @AfterViews
    public void init() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new CaterogyFragment_(), CATEGORY_FRAGMENT).commit();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);








    }




    @Override
    public void onCategorySelected(String category) {
        this.category = category;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,R.animator.slide_in_left, R.animator.slide_out_right);

        ListFeedFragment sharedElementFragment1 = new ListFeedFragment_();

        fragmentTransaction.replace(R.id.fragment, sharedElementFragment1, LIST_FEED_FRAGMENT).addToBackStack(null).commit();

        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setVisibilityForAllItem(boolean visibility) {
        if (this.menu == null) return;
        for (int index = 0; index < this.menu.size(); index++) {
            this.menu.getItem(index).setVisible(visibility);

        }

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
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,R.animator.slide_in_left, R.animator.slide_out_right);
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

    @Override
    public void setRunningFragment(FragmentEnum fragment) {
        switch (fragment) {
            case CATEROGY:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setVisibilityForAllItem(false);
                getSupportActionBar().setTitle(null);
                appBar.setExpanded(true);

                break;
            case LIST_FEED:
                getSupportActionBar().setTitle(category);

                if (feedId != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    ListFeedFragment_ listFeedFragment_ = (ListFeedFragment_) fragmentManager.findFragmentByTag(LIST_FEED_FRAGMENT);

                    setVisibilityForAllItem(false);
                    listFeedFragment_.scrollToIndex(feedId);
                }
                break;
            case FEED:
                setVisibilityForAllItem(true);
                break;

            default:
                break;


        }
    }

    @Override
    public void changeColor(Runnable runnable) {
        this.changeColor = runnable;
    }

    @Override
    public void changeTextSize(Runnable runnable) {
        this.changeTextSize = runnable;

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
    public void changeSize(int textSize) {
        config.edit().textSize().put(textSize).apply();
        if (changeTextSize != null) {
            changeTextSize.run();

        }
    }

    @Override
    public void changeColor(boolean isDarkMode) {
        config.edit().darkBackground().put(isDarkMode).apply();
        if (changeColor != null) {
            changeColor.run();

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share:
                share();
                break;
            case R.id.web:
                openSource();
                break;
            case R.id.setting:
                Boolean isDarkmode = config.darkBackground().get();
                int textSize = config.textSize().get();
                displaySettingBottomSheet = new DisplaySettingBottomSheet(isDarkmode,textSize);
                displaySettingBottomSheet.show(getSupportFragmentManager(), "bottom sheet");

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
