package com.content;

import android.util.Log;

public class TextContent extends Content {
	String text;

	public TextContent(String text) {
		Log.i("text",text);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
