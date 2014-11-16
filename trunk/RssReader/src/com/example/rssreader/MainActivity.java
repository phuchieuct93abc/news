package com.example.rssreader;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ListView;

import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById
	ListView listView;

	@Bean
	FeedListAdapter adapter;

	@AfterViews
	void afterView() {
		background();
	}

	@ItemClick
	public void listViewItemClicked(Feed clickedItem) {

		
		  Uri uri = Uri.parse(clickedItem.getLink()); Intent intent = new
		  Intent(Intent.ACTION_VIEW, uri); startActivity(intent);
		 

	}

	@UiThread
	void run() {
		listView.setAdapter(adapter);
		

	}

	@Background
	void background() {
		try {
			RssReader rssReader = new RssReader(
					"http://www.baomoi.com/Home/KHCN.rss");

			List<RssItem> rssItems = rssReader.getItems();
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
