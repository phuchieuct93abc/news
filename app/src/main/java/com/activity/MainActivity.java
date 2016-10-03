package com.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
import com.google.gson.Gson;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_main2)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

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
    @ViewById
    DrawerLayout mDrawerLayout;
    @Bean
    CategoryService categoryService;

    Menu menu;
    Integer feedId;
    Feed feedOnView;
    Runnable changeColor;
    Runnable changeTextSize;
    DisplaySettingBottomSheet displaySettingBottomSheet;
    String runningFragment;

    Feed selectedFeed;
    String selectedCategory;

    private static <Feed> List<com.model.Feed> stringToArray(String s, Class<com.model.Feed[]> clazz) {
        com.model.Feed[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("runningFragment", runningFragment);
        outState.putString("selectedCategory", selectedCategory);

        String listFeedJson = new Gson().toJson(categoryService.getListFeed());
        outState.putString("listFeed", listFeedJson);
        //aasd
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            runningFragment = (String) savedInstanceState.get("runningFragment");
            selectedFeed = (Feed) savedInstanceState.get("selectedFeed");
            selectedCategory = (String) savedInstanceState.get("selectedCategory");

            String listFeedJson = (String) savedInstanceState.get("listFeed");

            List<Feed> feeds = stringToArray(listFeedJson, Feed[].class);
            categoryService.setListFeed(feeds);
            Log.d("save", "get list feed from save");

        }

        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (runningFragment == null) {
            changeFragment(FragmentEnum.CATEROGY);
        }

    }

    private void changeFragment(FragmentEnum fragment) {
        switch (fragment) {
            case CATEROGY:
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, new CaterogyFragment_(), runningFragment).commit();

                break;
            case LIST_FEED:
                onCategorySelected(this.selectedCategory);

                break;
            case FEED:
                onSelectFeed(selectedFeed, null);
                break;

            default:
                break;
        }

    }

    @Override
    public void onCategorySelected(String category) {
        this.selectedCategory = category;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        ListFeedFragment sharedElementFragment1 = new ListFeedFragment_();

        fragmentTransaction.replace(R.id.fragment, sharedElementFragment1, FragmentEnum.LIST_FEED.toString()).addToBackStack(null).commit();

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
        setVisibilityForAllItem(true);
        startFeedViewFragment(bundle);
    }

    private void startFeedViewFragment(Bundle bundle) {
        FeedViewActivity_ sharedElementFragment2 = new FeedViewActivity_();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        sharedElementFragment2.setArguments(bundle);
        fragmentTransaction
                .replace(R.id.fragment, sharedElementFragment2, FragmentEnum.FEED.toString())
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
                //setVisibilityForAllItem(false);
                getSupportActionBar().setTitle(null);
                appBar.setExpanded(true);

                break;
            case LIST_FEED:
                getSupportActionBar().setTitle(selectedCategory);

                if (feedId != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    ListFeedFragment_ listFeedFragment_ = (ListFeedFragment_) fragmentManager.findFragmentByTag(FragmentEnum.LIST_FEED.toString());

                    setVisibilityForAllItem(false);
                    listFeedFragment_.scrollToIndex(feedId);
                    feedId = null;
                }
                break;
            case FEED:
                setVisibilityForAllItem(true);
                break;

            default:
                break;
        }
        runningFragment = fragment.toString();
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
                displaySettingBottomSheet = new DisplaySettingBottomSheet(isDarkmode, textSize);
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
