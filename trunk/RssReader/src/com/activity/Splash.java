package com.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;

import com.example.rssreader.R;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.splash)
public class Splash extends Activity {

	/** Duration of wait **/
	private final int SPLASH_DISPLAY_LENGTH = 2000;
	private Context context = this;

	@AfterViews
	void afterView() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(Splash.this, MainScreen_.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}

}