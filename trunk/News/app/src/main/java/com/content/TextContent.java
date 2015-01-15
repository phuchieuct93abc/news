package com.content;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class TextContent extends Content {
	String text;
	private final static int textSize = 25;

	public TextContent(String text, Context context) {
		super(context);
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
		return this.text;
	}

	public View toView() {
		TextView textView = new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setText("    "+toString());
		textView.setTextSize(TextContent.textSize);
		return textView;
	}

	
}
