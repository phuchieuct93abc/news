package com.activity;

import java.io.IOException;
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
	
	@Background
	void runBackground() {
		link = "http://m.baomoi.com/Home/CNTT/vtv.vn/Nguyen-Ha-Dong-vao-danh-sach-trieu-phu-lam-giau-tu-so-0-nho-Internet/15483968.epi";
		feedContent = feedService.getFeedContent(link);
		Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
		
		List<Content> contents = feedService.parseContent(doc,
				context);
		try {
			html = Jsoup.connect(link).followRedirects(true).get().html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setHTML(contents);
		Log.i("hieu","hieu");
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
