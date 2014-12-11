package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;
import com.services.FeedService;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById
	LoadMoreListView listView;

	@Bean
	FeedListAdapter adapter;

	@Bean
	FeedService feedService;
	private int numberOfPage = 1;
	Context context = this;

	@AfterViews
	void afterView() {
		background();
		setOnScrollListener();
	}

	private void setOnScrollListener() {

		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
			public void onLoadMore() {
				numberOfPage++;
				loadNextPage();
			}

		});
	}

	@Background
	void loadNextPage() {
		String nextLink = link.replace(".epi", "/p/" + numberOfPage + ".epi");
		List<Element> rssItems = feedService.getFeed(nextLink);
		adapter.setListDataMore(rssItems);
		updateList();
	}

	@UiThread
	void updateList() {
		adapter.notifyDataSetChanged();
		listView.onLoadMoreComplete();
	}

	@ItemClick
	public void listViewItemClicked(Feed clickedItem) {
//		dView_.intent(this).link(clickedItem.getLink()).start();
//		FeedView_.instantiate(context, "ABC").startActivity(intent)
		startActivity(new Intent(this, FeedViewActivity_.class));
	}

	@Extra
	String link;

	@UiThread
	void run() {
		listView.setAdapter(adapter);
	}

	@Background
	void background() {
		try {
			List<Element> rssItems = feedService.getFeed(link);
			adapter.setListData(rssItems);
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//Action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	SearchScreen_.intent(context).start();
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
