package com.feed;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.model.Feed;
import com.phuchieu.news.R;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.feed_view_big_image)
public class FeedItemView extends RelativeLayout {

    private static int cacheDuration = 1000 * 60 * 3;
    @ViewById
    TextView title;

    @ViewById
    ImageView imageView;
    @Bean
    HttpService httpService;


    public FeedItemView(Context context) {
        super(context);
    }

    public void bindDataToView(Feed feed) {
        Context context = getContext();
        if (!feed.isRead(context)) {
            title.setTextColor(Color.BLACK);
        } else {
            title.setTextColor(Color.WHITE);
        }
        title.setText(feed.getTitle());
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setMaxLines(2);


        httpService.loadImage(feed.getLandscapeAvatar(), imageView, null);


    }


}