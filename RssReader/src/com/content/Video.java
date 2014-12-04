package com.content;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Video extends Content {
	String iframHTML;

	public Video(String html, Context context) {
		super(context);
		this.iframHTML = html;
	}

	@Override
	public String toString() {
		return this.iframHTML;
	}
	
	
	public View toView() {
		
		TextView t = new TextView(context);

		Log.i("hieu",t.getTextSize()+"");

		t.setText(iframHTML);
		
		
	return t;
	}
	@Override
	public boolean isVideo() {
		return true;
	}
}
