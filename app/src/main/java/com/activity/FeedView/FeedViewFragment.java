package com.activity.FeedView;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.config.Config_;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment implements Html.ImageGetter {
    @ViewById
    TextView textViewContent;
    @ViewById
    ImageView imageView;
    @ViewById
    TextView title;
    @ViewById
    ProgressBar progressBar;
    @Bean
    HttpService httpService;
    @ViewById
    LinearLayout actionButtons;
    @Pref
    Config_ myPrefs;
    @ViewById
    NestedScrollView feed_wrapper;
    @ViewById
    LinearLayout content;
    @ViewById
    ProgressBar progress_bar;
    @ViewById
    TextView time;
    @ViewById
    TextView zoneName;
    @ViewById
            ImageView logo;

    Feed feed;


    @Bean
    FeedService feedService;
    private String TAG = "FeedViewFragment";


    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @Click(R.id.retry)
    void retry() {
        actionButtons.setVisibility(View.GONE);

        runBackground();
    }

    @Click(R.id.openSource)
    void openSource() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feed.getContentUrl()));
        startActivity(i);
    }

    @UiThread
    void initializeSetting() {
        httpService.loadImage(feed.getLandscapeAvatar(), imageView, progressBar);
        title.setText(feed.getTitle());
        zoneName.setText(feed.getZoneName());
        feed.getDate();

        Date itemDate =  DateUtil.getJavaDate(feed.getDate());

        String itemDateStr = new SimpleDateFormat("MM-dd HH:mm").format(itemDate);
        time.setText(itemDateStr);

        feedService.getIconOfUrl(feed.getContentUrl(),logo);



    }


    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            updateTextViewContent(contentHTML);
        } catch (Exception e) {
            getFailed();


        }
    }

    @UiThread
    void updateTextViewContent(String html) {

        Spanned spanned = Html.fromHtml(html, this, null);
        textViewContent.setText(spanned);
        progress_bar.setVisibility(View.GONE);
    }


    @UiThread
    void getFailed() {
        textViewContent.setText("Can't connect to server");
        actionButtons.setVisibility(View.VISIBLE);
    }


    @AfterInject
    void bindLinkToView() {
        initializeSetting();
        runBackground();

    }

    @AfterViews
    void afterView() {
        applyColor();
        applyTextsize();
    }


    @Override
    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        try {
            Point size = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(size);
            new LoadImage(textViewContent, getActivity().getApplicationContext(), size).execute(s, d);
        } catch (Exception e) {
        }


        return d;
    }

    public void applyColor() {
        Boolean blackColor = myPrefs.darkBackground().get();
        int textColor;
        int backgroundColor;
        if (blackColor) {
            textColor = getResources().getColor(R.color.light);
            backgroundColor = getResources().getColor(R.color.dark);
        } else {
            textColor = getResources().getColor(R.color.dark);
            backgroundColor = getResources().getColor(R.color.light);
        }
        textViewContent.setTextColor(textColor);
        title.setTextColor(textColor);
        zoneName.setTextColor(textColor);

        content.setBackgroundColor(backgroundColor);


        feed_wrapper.setBackgroundColor(backgroundColor);
        progress_bar.setBackgroundColor(backgroundColor);


    }

    public void applyTextsize() {
        int textSize = myPrefs.textSize().get();
        textViewContent.setTextSize((float) textSize * 5 + 14);

    }


}
