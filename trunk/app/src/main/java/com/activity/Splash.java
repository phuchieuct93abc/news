package com.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.activity.caterogy.CaterogyLivetile_;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash)
public class Splash extends Activity {
    private final static int SPLASH_DISPLAY_LENGTH = 1000;

    @AfterViews
    void afterView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CaterogyLivetile_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}