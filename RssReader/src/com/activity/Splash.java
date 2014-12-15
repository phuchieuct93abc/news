package com.activity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Window;

import com.phuchieu.news.R;
import com.services.FeedService;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.splash)
public class Splash extends Activity {

	/** Duration of wait **/
	private final int SPLASH_DISPLAY_LENGTH = 1000;
	private Context context = this;

	@AfterInject
	void afterView() {
		FeedService.setContext(getApplicationContext());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				CategoryScreen_.intent(context).start();
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}

}