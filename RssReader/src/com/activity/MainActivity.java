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

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullAndLoadListView.OnLoadMoreListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;
import com.services.FeedService;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity   {

	@ViewById
	PullAndLoadListView listView;

	@Bean
	FeedListAdapter adapter;

	private int numberOfPage = 1;
	Context context = this;

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
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
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				FeedService.clearCache();
				background();
			}
		});
	}

	@Background
	void loadNextPage() {
		
		String nextLink = FeedService.getLinkByPageNumber(link,numberOfPage);
		List<Feed> rssItems = FeedService.getFeedFromUrl(nextLink);
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
		FeedViewActivity_.intent(context)
				.extra("selectedLink", clickedItem.getLink())
				.extra("linkCategory", link).start();

	}

	@Extra
	String link;

	@UiThread
	void run() {
		listView.setAdapter(adapter);
		listView.onRefreshComplete();
		listView.onLoadMoreComplete();
	}

	@Background
	void background() {
		try {
			List<Feed> rssItems = FeedService.getFeedFromUrl(link);
			adapter.setListData(rssItems);
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Action bar
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