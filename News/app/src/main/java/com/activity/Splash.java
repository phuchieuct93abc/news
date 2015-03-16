package com.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.phuchieu.news.R;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash)
public class Splash extends Activity {

    private final static int SPLASH_DISPLAY_LENGTH = 1000;
    public static Context context;

    public static Context getContext() {
        return context;

    }

    @AfterViews
    void afterView() {
        context = getApplicationContext();
        FeedService.setContext(getBaseContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CategoryScreen_.intent(context).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        //test();

    }

}