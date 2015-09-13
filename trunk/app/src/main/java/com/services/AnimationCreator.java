package com.services;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

import com.phuchieu.news.R;

public class AnimationCreator extends Animation {
    public static void slide_down(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.maximize);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {


    }
}
