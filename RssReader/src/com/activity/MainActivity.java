package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rssreader.R;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.services.FeedService;
import com.shirwa.simplistic_rss.RssItem;

@Fullscreen
@EActivity(R.layout.activity_main)
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
		Toast.makeText(getApplicationContext(), link, Toast.LENGTH_SHORT)
		.show();
		listView.setAdapter(adapter);

	}

	@Background
	void background() {
		try {

			List<RssItem> rssItems = feedService.getFeed(link);
			for (RssItem rssItem : rssItems) {
				adapter.setData(rssItem);
			}
			run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
