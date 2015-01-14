package com.activity.ListFeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.phuchieu.news.R;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_list_feed)
public class ListFeed extends ActionBarActivity {
    @ViewById
    ViewPager pagerListFeed;
    PagerAdapterListFeed adapter;

    @Extra
    String link;


    @AfterViews
    void afterInjected() {
        setFragment();
        adapter.setLink(link);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void setFragment() {
        adapter = new PagerAdapterListFeed(getSupportFragmentManager());
        adapter.setContext(getApplicationContext());
        pagerListFeed.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }


}

class PagerAdapterListFeed extends FragmentStatePagerAdapter {
    public void setLink(String link) {
        this.link = link;
    }

    String link;
    List<String> categoryUrlList = new ArrayList<>();
    List<Tile> categoryList;


    public PagerAdapterListFeed(FragmentManager fm) {
        super(fm);

        categoryList = TileService.getList();
        for (Tile t : categoryList) {
            categoryUrlList.add(t.getUrl());
        }

    }


    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainActivity_();
        ((MainActivity) fragment).setContext(context);
        ((MainActivity) fragment).setLink(categoryUrlList.get(i));

        return fragment;
    }

    @Override
    public int getCount() {
        return categoryUrlList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getTitle();
    }
}
