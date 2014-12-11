package com.activity;

import java.io.IOException;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.content.Content;
import com.content.ImageContent;
import com.content.TextContent;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.phuchieu.news.R;
import com.services.FeedService;

@SuppressLint("SetJavaScriptEnabled")
@EActivity(R.layout.view)
public class FeedView extends Activity {
	@Extra
	String link;
	@ViewById
	TextView title;
	@ViewById
	LinearLayout layout;
	@Bean
	FeedService feedService;
	FeedContent feedContent;
	String contentHTML, html;
	NoteViewAdapter noteViewAdapter;
	FlipViewController flipView;
	Context context = this;

	@Background
	void runBackground() {
		feedContent = feedService.getFeedContent(link);
		Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
		List<Content> contents = feedService.parseContent(doc,
				getApplicationContext());
		try {
			html = Jsoup.connect(link).followRedirects(true).get().html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setHTML(contents);
	}

	@UiThread
	void setHTML(List<Content> contents) {
		for (Content content : contents) {

			addContent(content);

		}
		title.setText(feedContent.getTitle());
	}

	private void addContent(Content content) {

		View view = content.toView();
		layout.addView(view);
	}

	@AfterViews
	void bindLinkToView() {
		runBackground();
	};
}
