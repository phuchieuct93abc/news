package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

@EFragment
public class FeedViewFragment extends Fragment implements Html.ImageGetter, ObservableScrollViewCallbacks {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view, container, false);
    Log.i("set feed",feed.getContentID()+"");
        view.findViewById(R.id.imageView).setTransitionName(feed.getContentID()+"");
        return  view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.imageView).setTransitionName(feed.getContentID()+"");

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
        Log.i("finish",feed.getContentID()+"");
        ActivityCompat.startPostponedEnterTransition(getActivity());

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
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        Context context = getContext();

        int textPrimaryColor, textSecondaryColor, backgroundColor;
        if (blackColor) {
            textPrimaryColor = ContextCompat.getColor(context, R.color.text_primary_dark);
            textSecondaryColor = ContextCompat.getColor(context, R.color.text_secondary_dark);
            backgroundColor = ContextCompat.getColor(context, R.color.background_dark);

        } else {
            textPrimaryColor = ContextCompat.getColor(context, R.color.text_primary_light);
            textSecondaryColor = ContextCompat.getColor(context, R.color.text_secondary_light);
            backgroundColor = ContextCompat.getColor(context, R.color.background_light);
        }

        title.setTextColor(textPrimaryColor);
        textViewContent.setTextColor(textPrimaryColor);
        zoneName.setTextColor(textSecondaryColor);
        txtDate.setTextColor(textSecondaryColor);

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
        ViewHelper.setTranslationY(imageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }


}
