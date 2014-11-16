package com.feed;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rssreader.R;

@EViewGroup(R.layout.feed_view)
public class FeedItemView extends RelativeLayout {

	@ViewById
	TextView title;

	@ViewById
	TextView description;

	@ViewById
	WebView webView1;

	public FeedItemView(Context context) {
		super(context);
	}

	public void bind(Feed person) {

		title.setText(person.getTitle());
		description.setText(person.getContent());
		webView1.loadUrl(person.getImage());
		webView1.setClickable(false);
		webView1.setLongClickable(false);
		webView1.setFocusable(false);
		webView1.setFocusableInTouchMode(false);
		

	}

}