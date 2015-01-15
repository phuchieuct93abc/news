package com.activity.ListFeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.services.SearchService;

/**
 * Created by phuchieuct on 15/01/2015.
 */
class SearchAdapter extends FragmentStatePagerAdapter {
    String searchValue;

    Context context;

    public SearchAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainActivity_();
        ((MainActivity) fragment).setContext(context);
        ((MainActivity) fragment).setLink(SearchService.search(this.searchValue));

        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.searchValue;
    }
}
