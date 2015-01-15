package com.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.phuchieu.news.R;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash)
public class Splash extends Activity {

    /**
     * Duration of wait *
     */
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Context context = this;


    @AfterViews
    void afterView() {
        FeedService.setContext(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CategoryScreen_.intent(context).start();
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}