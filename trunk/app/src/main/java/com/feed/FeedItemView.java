package com.feed;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phuchieu.news.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.RootContext;
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
        Context context = getContext();
        if (!feed.isRead(context)) {
            title.setTextColor(Color.BLACK);
        } else {
            title.setTextColor(Color.WHITE);
        }
        title.setText(feed.getTitle());
        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(2);
        description.setText(feed.getContent());

        try {
            if(feed.getImage() == null || feed.getImage().trim().length()==0)throw new Exception();
            Picasso.with(context)
                    .load(feed.getImage())
                    .placeholder(R.drawable.news)
                    .error(R.drawable.news)
                    .into(imageView);
        } catch (Exception e) {
            Picasso.with(context).load(R.drawable.news).into(imageView);

        }


//        Ion.getDefault(getContext()).with(imageView).smartSize(true).placeholder(R.drawable.news).load(feed.getImage());
//        UrlImageViewHelper.setUrlDrawable(imageView, feed.getImage(), R.drawable.news, cacheTime);
    }
}