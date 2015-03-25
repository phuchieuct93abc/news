package com.activity.ListFeedView;

import android.content.Intent;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.phuchieu.news.R;
import com.quentindommerc.superlistview.SuperListview;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

@EActivity(R.layout.activity_list_feed)
public class ListFeed extends ActionBarActivity {
    @ViewById
    ViewPager pagerListFeed;
    PagerAdapterListFeed caterogyListAdapter = new PagerAdapterListFeed(getSupportFragmentManager());

    @ViewById
    SuperListview listView;
    private Timer timer = new Timer();


    @AfterViews
    void afterInjected() {
        setFragment();
        setToolbar();


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

/*        getSupportActionBar().setIcon(R.drawable.ic_launcher_2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setFragment() {
        caterogyListAdapter.setContext(getApplicationContext());
        pagerListFeed.setAdapter(caterogyListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem searchItem = menu.findItem(R.id.action_search);

        return super.onCreateOptionsMenu(menu);
    }






    @UiThread
    void assignAdapter(FragmentStatePagerAdapter adapter) {
        adapter.notifyDataSetChanged();
        pagerListFeed.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

