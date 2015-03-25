package com.activity.ListFeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import java.util.List;

class PagerAdapterListFeed extends FragmentStatePagerAdapter {
    List<Tile> categoryList;
    Context context;

    public PagerAdapterListFeed(FragmentManager fm) {
        super(fm);
        categoryList = TileService.getList();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainActivity_();
        ((MainActivity) fragment).setContext(context);
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 1 ? "Chua doc" : "Da doc";
    }
}
