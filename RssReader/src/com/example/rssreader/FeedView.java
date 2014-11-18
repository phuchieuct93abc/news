package com.example.rssreader;

import java.io.IOException;
import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aphidmobile.flip.FlipViewController;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.services.FeedService;

@EActivity
public class FeedView extends Activity {
	/*
	 * @ViewById WebView webView;
	 * 
	 * 
	 * 
	 * @AfterViews void bindLinkToView(){ webView.setWebViewClient(new
	 * WebViewClient()); webView.loadUrl(link);
	 * 
	 * }
	 */
	@Extra
	String link;

	@Bean
	FeedService feedService;
	FeedContent feedContent;

	String contentHTML;
	NoteViewAdapter noteViewAdapter;
	FlipViewController flipView;
	

	@Background
	void runBackground() {
		feedContent= feedService.getFeedContent(link);
		Log.i("hieu",feedContent.toString());
	//	setHTML(feedService.getResponseFromUrl(link));

	}

	@UiThread
	void setHTML(String html) {
		
		
		
		/*ArrayList<String> notes = new ArrayList<String>();

		notes.add(html);
		notes.add(html);
		notes.add(html);
		flipView.setAdapter(new NoteViewAdapter(this, notes));*/

	}

	@AfterViews
	void bindLinkToView() {
		runBackground();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		flipView = new FlipViewController(this, FlipViewController.VERTICAL);

		setContentView(flipView);

	}

}
