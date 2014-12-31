package com.phuchieu.news;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
    @AfterViews
    void afterExtra() {
        Log.i("Hieu", "Hello");
        Toast.makeText(this,"dasdasd",Toast.LENGTH_LONG).show();

    }

}

