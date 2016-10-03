package com.activity.FeedView;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activity.MainActivityInterface;
import com.config.Config_;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.model.Feed;
import com.nineoldandroids.view.ViewHelper;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.Date;

@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment implements Html.ImageGetter, ObservableScrollViewCallbacks {
    private static String testIframe = "<iframe width=\"1280\" height=\"720\" src=\"https://www.youtube.com/embed/19MZTc_uQxU\" frameborder=\"0\" allowfullscreen></iframe>";
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
    ObservableScrollView feed_wrapper;
    @ViewById
    ConstraintLayout constrainLayout;
    @ViewById
    ProgressBar progress_bar;
    @ViewById
    TextView time;
    @ViewById
    TextView zoneName;
    @ViewById
    ImageView logo;
    @ViewById
    TextView txtDate;
    Feed feed;
    @Bean
    FeedService feedService;
    MainActivityInterface mainActivityInterface;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("feed", feed);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {

            feed = (Feed) savedInstanceState.get("feed");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivityInterface = (MainActivityInterface) activity;

    }

    @UiThread
    void initializeSetting() {
        httpService.loadImage(feed.getLandscapeAvatar(), imageView, progressBar);
        title.setText(feed.getTitle());
        zoneName.setText(feed.getZoneName());
        feed.getDate();
        feedService.getIconOfUrl(feed.getContentUrl(), logo);
        String formatted = getFormattedDate();
        txtDate.setText(formatted);
        feed_wrapper.setScrollViewCallbacks(this);


    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    private String getFormattedDate() {
        Long valueFromClient = new Double(feed.getDate()).longValue();

        Date date = new Date(valueFromClient);

        return new SimpleDateFormat("dd-MM HH:mm").format(date);
    }


    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            updateTextViewContent(contentHTML);

        } catch (Exception e) {
            Log.e("ERROR", "Can't get content", e);
            getFailed();


        }
    }

    @UiThread
    void updateTextViewContent(String html) {

        Spanned spanned = Html.fromHtml(html, this, null);
        if (spanned != null && textViewContent != null) {
            textViewContent.setText(spanned);
            progress_bar.setVisibility(View.GONE);

        }
    }


    @UiThread
    void getFailed() {
        textViewContent.setText("Can't connect to server");
        actionButtons.setVisibility(View.VISIBLE);
    }


    @AfterInject
    void bindLinkToView() {


    }

    @AfterViews
    void afterView() {
        applyColor();
        applyTextsize();
        initializeSetting();
        runBackground();
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
        txtDate.setTextColor(textColor);
        constrainLayout.setBackgroundColor(backgroundColor);


        feed_wrapper.setBackgroundColor(backgroundColor);
        progress_bar.setBackgroundColor(backgroundColor);


    }

    public void applyTextsize() {
        int textSize = myPrefs.textSize().get();
        textViewContent.setTextSize((float) textSize * 5 + 14);

    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        int baseColor = getResources().getColor(R.color.red);
        float alpha = Math.min(1, (float) scrollY / 150);
        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(imageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (scrollState == ScrollState.UP) {
            mainActivityInterface.isExpandActionBar(false);

        } else if (scrollState == ScrollState.DOWN) {
            mainActivityInterface.isExpandActionBar(true);

        }
    }

}
