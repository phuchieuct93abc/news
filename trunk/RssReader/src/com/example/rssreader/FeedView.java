package com.example.rssreader;

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
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.content.Content;
import com.content.TextContent;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.services.FeedService;

@EActivity(R.layout.view)
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

	@ViewById
	TextView title;
	@ViewById
	LinearLayout layout;
	@ViewById
	WebView content;
	@Bean
	FeedService feedService;
	FeedContent feedContent;

	String contentHTML;
	NoteViewAdapter noteViewAdapter;
	FlipViewController flipView;

	@Background
	void runBackground() {
		feedContent = feedService.getFeedContent(link);
		Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
		List<Content> contents = feedService.parseContent(doc);
		setHTML(contents);


		/*
		 * contentList.add(new TextContent("ABCDEF")); contentList.add(new
		 * ImageContent("http://")); TextContent text =
		 * (TextContent)contentList.get(0);
		 */

	}

	@UiThread
	void setHTML(List<Content> contents) {
		
		for(Content content : contents){
			TextView text = new TextView(getApplicationContext());
			text.setText(((TextContent)content).toString());
			layout.addView(text);
			Log.i("hieu",((TextContent)content).toString());
		}
		//title.setText(feedContent.getTitle());
/*		content.loadData(feedContent.getContentHTML(),
				"text/html; charset=UTF-8", null);*/

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

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); flipView = new
	 * FlipViewController(this, FlipViewController.VERTICAL);
	 * setContentView(flipView);
	 * 
	 * }
	 */

}
