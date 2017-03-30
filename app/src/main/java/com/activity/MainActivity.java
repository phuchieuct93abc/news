package com.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.activity.Fragments.DisplaySettingBottomSheet;
import com.activity.fragment_activity.CaterogyFragment_;
import com.activity.fragment_activity.ListFeedFragment;
import com.activity.fragment_activity.ListFeedFragment_;
import com.config.Config_;
import com.google.gson.Gson;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.SharedElementHelper;
import com.services.ViewModeEnum;

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
    @Pref
    Config_ config;

    @Bean
    CategoryService categoryService;

    Integer feedId;
    Feed feedOnView;
    Runnable changeColor;
    Runnable changeTextSize;
    Runnable refreshListFeed;
    DisplaySettingBottomSheet displaySettingBottomSheet;
    String runningFragment;

    Feed selectedFeed;
    String selectedCategory;
    Menu menu;
    ListFeedFragment sharedElementFragment1;


    private static List<com.model.Feed> stringToArray(String s, Class<com.model.Feed[]> clazz) {
        com.model.Feed[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("runningFragment", runningFragment);
        outState.putString("selectedCategory", selectedCategory);

        String listFeedJson = new Gson().toJson(categoryService.getListFeed());
        outState.putString("listFeed", listFeedJson);
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

        sharedElementFragment1 = new ListFeedFragment_();
        fragmentTransaction.replace(R.id.fragment, sharedElementFragment1, FragmentEnum.LIST_FEED.toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onSelectFeed(Feed feed, View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("feedId", feed.getContentID());
        startFeedViewFragment(bundle, (ImageView) v);
    }

    private void startFeedViewFragment(Bundle bundle, ImageView shareElement) {
        FeedViewActivity_ sharedElementFragment2 = new FeedViewActivity_();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in,R.anim.fade_out);
        sharedElementFragment2.setArguments(bundle);

        try {
            new SharedElementHelper().setContext(this).
                    setSharedElement(sharedElementFragment2, sharedElementFragment1);
        } catch (Exception e) {
            Log.e("hieu", "asd", e);
        }


        fragmentTransaction
                .replace(R.id.fragment, sharedElementFragment2, FragmentEnum.FEED.toString())
                .addToBackStack(null)
                .addSharedElement(shareElement, shareElement.getTransitionName())
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
        runningFragment = fragment.toString();

        invalidOptionMenu();
        switch (fragment) {
            case CATEROGY:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setTitle(null);


                break;
            case LIST_FEED:
                try {
                    getSupportActionBar().setTitle(selectedCategory);


                    if (feedId != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        sharedElementFragment1 = (ListFeedFragment_) fragmentManager.findFragmentByTag(FragmentEnum.LIST_FEED.toString());
                        sharedElementFragment1.scrollToIndex(feedId);
                        feedId = null;
                    }
                } catch (Exception ex) {
                    Log.e("hieu", "Can't scroll to selected feed", ex);

                } finally {
                    break;
                }

            case FEED:
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
    public void setRefreshListFeed(Runnable runnable) {
        this.refreshListFeed = runnable;
    }

    private void runRefreshListFeed() {
        if (this.refreshListFeed != null) {
            this.refreshListFeed.run();

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.unreadFeed:
                onReadModePressed(ViewModeEnum.UNREAD_FEED);
                break;
            case R.id.readFeed:
                onReadModePressed(ViewModeEnum.READ_FEED);
                break;
            case R.id.allFeed:
                onReadModePressed(ViewModeEnum.ALL_FEED);
                break;

            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.refresh:
                runRefreshListFeed();
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

    private void onReadModePressed(ViewModeEnum viewModeEnum) {

        config.edit().viewMode().put(viewModeEnum.getValue()).apply();
        updateTextForViewMode();
        runRefreshListFeed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        if (runningFragment == null) {
            getMenuInflater().inflate(R.menu.menu_empty, menu);
        } else {


            switch (FragmentEnum.valueOf(runningFragment)) {
                case CATEROGY:
                    getMenuInflater().inflate(R.menu.menu_empty, menu);
                    break;
                case LIST_FEED:
                    getMenuInflater().inflate(R.menu.menu_list_feed, menu);
                    updateTextForViewMode();
                    break;
                case FEED:
                    getMenuInflater().inflate(R.menu.menu_feed_view, menu);
                    break;

                default:
                    break;
            }
        }
        return true;
    }


    private void invalidOptionMenu() {

        this.invalidateOptionsMenu();

    }

    private void updateTextForViewMode() {
        MenuItem viewModeMenu = this.menu.findItem(R.id.viewModeItem);
        ViewModeEnum selectedViewMode = ViewModeEnum.getByCode(config.viewMode().get());
        switch (selectedViewMode) {
            case ALL_FEED:
                viewModeMenu.setTitle(R.string.readAll);
                break;
            case UNREAD_FEED:
                viewModeMenu.setTitle(R.string.onlyUnread);
                break;
            case READ_FEED:
                viewModeMenu.setTitle(R.string.onlyRead);
                break;
        }


    }

}
