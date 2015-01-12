package com.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.phuchieu.news.R;
import com.services.FeedService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_list_feed)
public class ListFeed extends FragmentActivity {
    @ViewById
    ViewPager pagerListFeed;
    PagerAdapterListFeed adapter;
    @AfterViews
    void afterInjected(){
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        FeedService.setContext(getApplicationContext());
        adapter = new PagerAdapterListFeed(getSupportFragmentManager());
        adapter.setContext(getApplicationContext());
        pagerListFeed.setAdapter(adapter);
    }

    private FragmentManager.OnBackStackChangedListener getListener()
    {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null)
                {
                    Fragment fragment = manager.getFragments()
                            .get(1);
                    fragment.onResume();
                }
            }
        };

        return result;
    }
}
class PagerAdapterListFeed extends FragmentStatePagerAdapter {




    public PagerAdapterListFeed(FragmentManager fm) {
        super(fm);
    }



    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainActivity_();
        ( (MainActivity)fragment).setContext(context);
        ( (MainActivity)fragment).setLink("http://m.baomoi.com/Home/mostrecent/KHCN/p/1.epi");

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
	return "abc";
    }
}
