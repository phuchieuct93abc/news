package com.feed;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phuchieu.news.R;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
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
    @Bean
    HttpService httpService;
    private static int cacheDuration = 1000*60*3;


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

//
//        int width = feed.getWidth();
//        int height = feed.getHeight();
//        try{
//       if(height/width*1020 >0) {
//           RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(1020, height / width * 1020);
//                  imageView.setLayoutParams(parms);}
//
//       }catch(Exception e){
//    Log.d("hieu",height+" "+width);
//
//        }
        httpService.loadImage(feed,imageView);


//        UrlImageViewHelper.setUrlDrawable(imageView, feed.getImage(), R.drawable.news, cacheTime);
    }


}