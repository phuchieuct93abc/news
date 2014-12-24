package com.feed;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.phuchieu.news.R;

@EViewGroup(R.layout.feed_view)
public class FeedItemView extends RelativeLayout {

	@ViewById
	TextView title;

	@ViewById
	TextView description;

	@ViewById
	TextView isRead;

	@ViewById
	ImageView imageView;

	public FeedItemView(Context context) {
		super(context);
	}

	public void bindDataToView(Feed feed) {
		Log.i("hieu",feed.isRead().toString());

		if(feed.isRead()){
			isRead.setVisibility(View.VISIBLE);

		}else{
			isRead.setVisibility(View.GONE);

		}
		title.setText(feed.getTitle());
		description.setText(feed.getContent());
		UrlImageViewHelper.setUrlDrawable(imageView, feed.getImage());
	}

}