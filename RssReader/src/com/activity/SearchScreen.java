package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.renderscript.Element;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rssreader.R;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.services.SearchService;

@EActivity(R.layout.search_screen)
public class SearchScreen extends Activity {
	@ViewById
	EditText editText1;
	@ViewById
	Button button1;
	@ViewById
	ListView listView1;

	@Bean
	FeedListAdapter adapter;

	List<org.jsoup.nodes.Element> feeds;
	@AfterViews
	void init() {
		runBackGround();
	}

	@Background
	void runBackGround() {
		feeds = SearchService.search("smartphone");
		
		for (org.jsoup.nodes.Element feed : feeds) {
			adapter.setData(feed);
		}
		uIThread();
	}

	@UiThread
	void uIThread() {
		//Toast.makeText(getApplicationContext(), feeds.size()	, Toast.LENGTH_SHORT).show();
		listView1.setAdapter(adapter);
	}
}
