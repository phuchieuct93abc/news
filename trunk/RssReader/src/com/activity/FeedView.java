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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aphidmobile.flip.FlipViewController;
import com.content.Content;
import com.content.ImageContent;
import com.content.TextContent;
import com.example.rssreader.R;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.services.FeedService;

@SuppressLint("SetJavaScriptEnabled") @EActivity(R.layout.view)
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

			if (content instanceof TextContent) {
				if (content.toString().indexOf("iframe") > -1) {
					addVideo(content);
				} else {
					addText(content);
				}
			}
			if (content instanceof ImageContent) {
				addImage(content);
			}

		}
		title.setText(feedContent.getTitle());
	}

	private void addImage(Content content) {
		ImageView imageView = new ImageView(context);
		// imageView.setLayoutParams(new
		// ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

		UrlImageViewHelper.setUrlDrawable(imageView,
				content.toString(), R.drawable.loading);
		layout.addView(imageView);
	}

	private void addText(Content content) {
		TextView textView = new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setText(content.toString());
		textView.setTextSize(20);
		layout.addView(textView);
	}

	private void addVideo(Content content) {
		WebView webView = new WebView(getBaseContext());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.loadData("<html><body>" + content.toString()
				+ "</body></html>", "text/html", "utf-8");
		layout.addView(webView);
	}

	@AfterViews
	void bindLinkToView() {
		runBackground();
	};
}
