package com.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash)
public class Splash extends Activity {
    private final static int SPLASH_DISPLAY_LENGTH = 1000;


    private Activity activity = this;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {


            Intent i = new Intent(activity, MainActivity_.class);
            ImageView sharedView = (ImageView) findViewById(R.id.icon);
            String transitionName = getString(R.string.sharedIconName);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(Splash.this, sharedView, transitionName);
            startActivity(i, transitionActivityOptions.toBundle());
            Splash.this.finish();

        }
    };

    @AfterViews
    void afterView() {


        new Handler().postDelayed(runnable, SPLASH_DISPLAY_LENGTH);

    }
}