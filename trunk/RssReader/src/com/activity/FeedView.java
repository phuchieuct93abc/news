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

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

@EActivity(R.layout.view)
public class FeedView extends Activity {
	/*
	 * 
	 * 
	 * 
	 * 
	 * @AfterViews void bindLinkToView(){ webView.setWebViewClient(new
	 * WebViewClient()); webView.loadUrl(link);
	 * 
	 * }
	 */
	@ViewById
	WebView webView1;
	@Extra
	String link;

	@ViewById
	TextView title;
	@ViewById
	LinearLayout layout;

	@Bean
	FeedService feedService;
	FeedContent feedContent;

	String contentHTML;
	NoteViewAdapter noteViewAdapter;
	FlipViewController flipView;
	Context context = this;

	@Background
	void runBackground() {
		Log.i("hieu", link);
		feedContent = feedService.getFeedContent(link);
		Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
		List<Content> contents = feedService.parseContent(doc,
				getApplicationContext());
		try {
			html = Jsoup
					.connect(
							"http://m.baomoi.com/Home/CNTT/gamek.vn/Tang-300-Gift-Code-Tan-Ngoa-Long-mung-ngay-mo-cua/15346569.epi")
					.followRedirects(true).get().html();
			Log.i("hieu",html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setHTML(contents);
	}

	String html;

	@UiThread
	void setHTML(List<Content> contents) {
		for (Content content : contents) {

			if (content instanceof TextContent) {
				if (content.toString().indexOf("iframe") > -1) {
					WebView webView = new WebView(getBaseContext());
					webView.getSettings().setJavaScriptEnabled(true);
					webView.setWebChromeClient(new WebChromeClient());
					webView.loadData("<html><body>" + content.toString()
							+ "</body></html>", "text/html", "utf-8");
					layout.addView(webView);
				} else {
					TextView textView = new TextView(context);
					textView.setTextColor(Color.BLACK);
					textView.setText(content.toString());
					textView.setTextSize(20);
					layout.addView(textView);
				}

			}
			if (content instanceof ImageContent) {
				ImageView imageView = new ImageView(context);

				UrlImageViewHelper.setUrlDrawable(imageView,
						content.toString(), R.drawable.loading);
				layout.addView(imageView);
			}

		}
		title.setText(feedContent.getTitle());

		//webView1.loadData(html, "text/html; charset=UTF-8", null);
	/*	webView1.set
		webView1.loadUrl("http://www.baomoi.com/Home/KhoaHoc-TuNhien/kienthuc.net.vn/Sinh-vat-co-bo-mat-quy-du-xuat-hien-o-vung-bien-My/15346791.epi");*/

		/*
		 * content.loadData(feedContent.getContentHTML(),
		 * "text/html; charset=UTF-8", null);
		 */

		/*
		 * ArrayList<String> notes = new ArrayList<String>();
		 * 
		 * notes.add(html); notes.add(html); notes.add(html);
		 * flipView.setAdapter(new NoteViewAdapter(this, notes));
		 */

	}

	@AfterViews
	void bindLinkToView() {
		runBackground();
	};

}
