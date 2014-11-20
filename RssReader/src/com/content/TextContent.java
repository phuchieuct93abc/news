package com.content;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TextContent extends Content {
	String text;

	public TextContent(String text,Context context) {
		super(context);
		Log.i("text",text);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.text;
	}
	@Override
	public View  toView() {
		TextView textView = new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setText(this.text);
		return textView;
	}
}
