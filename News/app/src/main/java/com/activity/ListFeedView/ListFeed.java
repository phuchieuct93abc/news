package com.activity.ListFeedView;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

@EActivity(R.layout.activity_list_feed)
public class ListFeed extends ActionBarActivity {
    private final long DELAY = 500;
    @ViewById
    ViewPager pagerListFeed;
    PagerAdapterListFeed caterogyListAdapter = new PagerAdapterListFeed(getSupportFragmentManager());

    SearchAdapter searchAdapter = new SearchAdapter(getSupportFragmentManager());
    @Extra
    String link;
    private Timer timer = new Timer();

    @AfterViews
    void afterInjected() {
        setFragment();
        caterogyListAdapter.setLink(link);
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
        searchAdapter.setContext(getApplicationContext());
        pagerListFeed.setAdapter(caterogyListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                setSearchView(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setSearchView(final String searchValue) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!searchValue.isEmpty()) {
                    performSearch(searchValue);

                } else {
                    assignAdapter(caterogyListAdapter);

                }

            }

        }, DELAY);
    }

    @Background
    void performSearch(String searchValue) {
        searchAdapter.setSearchValue(searchValue);
        assignAdapter(searchAdapter);


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

