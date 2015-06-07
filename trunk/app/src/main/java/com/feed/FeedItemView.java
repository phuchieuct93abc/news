package com.feed;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phuchieu.news.R;
import com.squareup.picasso.Picasso;
import com.styles.Animate;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.feed_view_big_image)
public class FeedItemView extends RelativeLayout {

    public static int cacheTime = 60 * 60;
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
            title.setTextColor(Color.BLACK);

        } else {
            title.setTextColor(Color.WHITE);


        }

        title.setText(feed.getTitle());
        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(2);
        description.setText(feed.getContent());

        try {
            Animate animate = new Animate();
            Picasso.with(getContext())
                    .load(feed.getImage())
                    .placeholder(R.drawable.news).fit()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Ion.getDefault(getContext()).with(imageView).smartSize(true).placeholder(R.drawable.news).load(feed.getImage());
//        UrlImageViewHelper.setUrlDrawable(imageView, feed.getImage(), R.drawable.news, cacheTime);
    }
}