package com.example.rssreader;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.services.FeedService;
import com.shirwa.simplistic_rss.RssItem;

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

		FeedView_.intent(this).link(clickedItem.getLink()).start() ;
	

	}

	@UiThread
	void run() {
		listView.setAdapter(adapter);
		

	}

	@Background
	void background() {
	
		
		
		
		
		try {
		List<RssItem> rssItems = feedService.getFeed("http://www.baomoi.com/Home/KHCN.rss");
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
