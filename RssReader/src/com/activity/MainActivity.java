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
import org.androidannotations.annotations.WindowFeature;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.view.Window;
import android.widget.ListView;

import com.example.rssreader.R;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.services.FeedService;

@EActivity(R.layout.activity_main)
@WindowFeature({ Window.FEATURE_NO_TITLE })
public class MainActivity extends Activity {

	@ViewById
	ListView listView;

	@Bean
	FeedListAdapter adapter;

	@Bean
	FeedService feedService;

	@AfterViews
	void afterView() {
		background();

	}

	@ItemClick
	public void listViewItemClicked(Feed clickedItem) {
		FeedView_.intent(this).link(clickedItem.getLink()).start();
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
}
