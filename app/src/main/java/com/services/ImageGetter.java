package com.services;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.widget.TextView;

import com.activity.FeedView.LoadImage;

/**
 * Created by ACER on 3/15/2017.
 */

public class ImageGetter implements Html.ImageGetter {
    FragmentActivity activity;
    TextView textView;

    public ImageGetter(FragmentActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;

    }

    @Override
    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        try {
            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(size);
            new LoadImage(textView, activity.getApplicationContext(), size).execute(s, d);
        } catch (Exception e) {
        }


        return d;
    }
}
