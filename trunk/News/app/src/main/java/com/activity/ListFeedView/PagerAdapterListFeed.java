package com.activity.ListFeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import java.util.ArrayList;
import java.util.List;

class PagerAdapterListFeed extends FragmentStatePagerAdapter {
    String link;
    List<String> categoryUrlList = new ArrayList<>();
    List<Tile> categoryList;
    Context context;
    public PagerAdapterListFeed(FragmentManager fm) {
        super(fm);
        categoryList = TileService.getList();
        for (Tile t : categoryList) {
            categoryUrlList.add(t.getUrl());
        }
    }

    public void setLink(String link) {
        this.link = link;
    }

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
