package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rssreader.R;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.services.SearchService;

@EActivity(R.layout.search_screen)
public class SearchScreen extends Activity {
	@ViewById
	Button button1;
	@ViewById
	ListView listView;

	@Bean
	FeedListAdapter adapter;
	
	@TextChange
	 void SearchTextTextChanged(TextView hello, CharSequence text) {
		Log.i("hieu",text.toString());
		performSearch(text.toString());
	 }


	List<org.jsoup.nodes.Element> feeds;
	@AfterViews
	void init() {
		performSearch("Smart phone");
	}

	@Background
	void performSearch(String key) {
		feeds = SearchService.search(key);
		adapter.clear();
		
		for (org.jsoup.nodes.Element feed : feeds) {
			adapter.setData(feed);
		}
		uIThread();
	}

	@UiThread
	void uIThread() {
		listView.setAdapter(adapter);
	}
	@ItemClick
	public void listViewItemClicked(Feed clickedItem) {
		String link= clickedItem.getLink();
		link = link.replace("http://www", "http://m");
		FeedView_.intent(this).link(link).start();

	}
	
}
