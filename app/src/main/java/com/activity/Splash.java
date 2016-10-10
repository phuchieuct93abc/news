package com.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.activeandroid.query.Select;
import com.model.Item;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash)
public class Splash extends Activity {
    private final static int SPLASH_DISPLAY_LENGTH = 1000;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MainActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
            Splash.this.finish();
        }
    };

    @AfterViews
    void afterView() {
        Item t = new Select().from(Item.class).orderBy("RANDOM()").executeSingle();
        if (t != null) {

            Log.d("hieu", Item.getRandom().getName());

        }

        new Handler().postDelayed(runnable, SPLASH_DISPLAY_LENGTH);

    }
}