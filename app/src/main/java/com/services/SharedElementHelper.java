package com.services;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.activity.FeedView.FeedViewActivity_;
import com.activity.fragment_activity.ListFeedFragment;
import com.phuchieu.news.R;

/**
 * Created by phuchieuct on 30/03/2017.
 */

public class SharedElementHelper {

    public SharedElementHelper setContext(Context context) {
        this.context = context;
        return this;
    }

    Context context;

    public void setSharedElement(Fragment sharedElementFragment2, Fragment sharedElementFragment1) throws Exception{
        TransitionInflater inflator = TransitionInflater.from(context);
        Transition changeImageTransform = inflator.inflateTransition(R.transition.change_image_transform);

        Transition fadeTransition = inflator.inflateTransition(android.R.transition.fade);


        sharedElementFragment1.setSharedElementReturnTransition(changeImageTransform);
        sharedElementFragment1.setSharedElementEnterTransition(changeImageTransform);
        sharedElementFragment1.setEnterTransition(fadeTransition);
        sharedElementFragment1.setExitTransition(fadeTransition);
        sharedElementFragment2.setEnterTransition(fadeTransition);
        sharedElementFragment2.setExitTransition(fadeTransition);
        sharedElementFragment2.setSharedElementReturnTransition(changeImageTransform);
        sharedElementFragment2.setSharedElementEnterTransition(changeImageTransform);
    }
}
