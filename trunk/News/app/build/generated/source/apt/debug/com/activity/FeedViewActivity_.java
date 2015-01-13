//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.phuchieu.news.R.id;
import com.phuchieu.news.R.layout;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class FeedViewActivity_
    extends FeedViewActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String LINK_CATEGORY_EXTRA = "linkCategory";
    public final static String LINK_EXTRA = "selectedLink";
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.view_swipe);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectExtras_();
        requestWindowFeature(1);
        run();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static FeedViewActivity_.IntentBuilder_ intent(Context context) {
        return new FeedViewActivity_.IntentBuilder_(context);
    }

    public static FeedViewActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new FeedViewActivity_.IntentBuilder_(fragment);
    }

    public static FeedViewActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new FeedViewActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        pager = ((ViewPager) hasViews.findViewById(id.pager));
    }

    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(LINK_CATEGORY_EXTRA)) {
                linkCategory = extras_.getString(LINK_CATEGORY_EXTRA);
            }
            if (extras_.containsKey(LINK_EXTRA)) {
                link = extras_.getString(LINK_EXTRA);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    @Override
    public void setSelectedPage(final int index) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                FeedViewActivity_.super.setSelectedPage(index);
            }

        }
        );
    }

    @Override
    public void runUI() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                FeedViewActivity_.super.runUI();
            }

        }
        );
    }

    @Override
    public void updateAdapter() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                FeedViewActivity_.super.updateAdapter();
            }

        }
        );
    }

    @Override
    public void runBackground() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    FeedViewActivity_.super.runBackground();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void loadMoreData() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    FeedViewActivity_.super.loadMoreData();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<FeedViewActivity_.IntentBuilder_>
    {

        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, FeedViewActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), FeedViewActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), FeedViewActivity_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent, requestCode);
                } else {
                    super.startForResult(requestCode);
                }
            }
        }

        public FeedViewActivity_.IntentBuilder_ linkCategory(String linkCategory) {
            return super.extra(LINK_CATEGORY_EXTRA, linkCategory);
        }

        public FeedViewActivity_.IntentBuilder_ link(String link) {
            return super.extra(LINK_EXTRA, link);
        }

    }

}