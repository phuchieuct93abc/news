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
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
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
	@ViewById WebView webView1; 
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

	@Background
	void runBackground() {
		feedContent = feedService.getFeedContent(link);
		Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
		List<View> contents = feedService.parseContent(doc,getApplicationContext());
		setHTML(contents);


		/*
		 * contentList.add(new TextContent("ABCDEF")); contentList.add(new
		 * ImageContent("http://")); TextContent text =
		 * (TextContent)contentList.get(0);
		 */

	}

	@UiThread
	void setHTML(List<View> contents) {
		
		for(View content : contents){
			try{
					TextView textView = ((TextView)content);
					if(textView.getText().toString().indexOf("iframe") > -1){
						WebView webView = new WebView(getBaseContext());
						webView.getSettings().setJavaScriptEnabled(true);
						webView.setWebChromeClient(new WebChromeClient());
						webView.loadData("<html><body>"+textView.getText().toString()+"</body></html>", "text/html", "utf-8");
						Log.i("hieu","<html><body>"+textView.getText().toString()+"</body></html>");

						layout.addView(webView);
					}
				
			

			}catch(Exception e){
				
			}
			layout.addView(content);
			
		}
		title.setText(feedContent.getTitle());
		
		webView1.loadData(feedContent.getContentHTML(),
				"text/html; charset=UTF-8", null);
		
		
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

	
}
