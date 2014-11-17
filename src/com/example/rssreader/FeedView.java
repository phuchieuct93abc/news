package com.example.rssreader;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aphidmobile.flip.FlipViewController;
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

	String contentHTML;
	NoteViewAdapter noteViewAdapter;
	FlipViewController flipView;

	@Background
	void runBackground() {
		setHTML(feedService.getResponseFromUrl(link));

	}

	@UiThread
	void setHTML(String html) {
		Log.i("hieu",html);
		ArrayList<String> notes = new ArrayList<String>();
		notes.add(html);
		notes.add(html);
		notes.add(html);
		notes.add(html);
		flipView.setAdapter(new NoteViewAdapter(this, notes));

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
