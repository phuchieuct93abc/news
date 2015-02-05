package com.feed;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.phuchieu.news.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.feed_view_big_image)
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
        if (!feed.isRead()) {
//                isRead.setVisibility(View.VISIBLE);
            title.setTextColor(Color.BLACK);

        } else {
//                isRead.setVisibility(View.GONE);
            title.setTextColor(Color.WHITE);


        }

        title.setText(feed.getTitle());
        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(2);
        description.setText(feed.getContent());

//        Ion.with(imageView).load( feed.getImage());
        UrlImageViewHelper.setUrlDrawable(imageView, feed.getImage(), R.drawable.news, UrlImageViewHelper.CACHE_DURATION_ONE_DAY);
    }
}