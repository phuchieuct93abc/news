package com.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService_JSON;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

@EActivity(R.layout.splash)
public class Splash extends Activity {

    private final static int SPLASH_DISPLAY_LENGTH = 2000;
    private Context context = this;


    @AfterViews
    void afterView() {
        FeedService.setContext(getBaseContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CategoryScreen_.intent(context).start();
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        //test();

    }

}