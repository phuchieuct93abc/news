package com.services;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.phuchieu.news.R;

public class AnimationCreator {
    public static void slide_left(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_left);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_right(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_right);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


}
