package com.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.content.Content;
import com.content.TextContent;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.phuchieu.news.R;
import com.services.FeedService;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment {
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
	Context context;

	public void setContext(Context context){
		this.context = context;
		
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@Background
	void runBackground() {
		try {

			feedContent = feedService.getFeedContent(link);
			Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());

			List<Content> contents = feedService.parseContent(doc,				context);

			setHTML(contents);
		} catch (Exception e) {
			
			List<Content> contents = new ArrayList<Content>();
			TextContent t=new TextContent("Cannot get content", context);
			contents.add(t);
			setHTML(contents);
			
		}
	}

	@UiThread
	void setHTML(List<Content> contents) {
		for (Content content : contents) {
			addContent(content);
		}
		//title.setText(feedContent.getTitle());
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
