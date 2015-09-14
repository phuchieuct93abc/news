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
    public static void slide_right(Context ctx, View v,final Runnable runnable) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_right);
        if(runnable!=null){



            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    runnable.run();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


}
