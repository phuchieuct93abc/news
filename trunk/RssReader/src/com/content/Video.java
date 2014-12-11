package com.content;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

public class Video extends Content {
	String iframHTML;
	Context context;

	public Video(String html, Context context) {
		super(context);
		this.iframHTML = html;
		this.context = context;
	}

	@Override
	public String toString() {
		return this.iframHTML;
	}

	public View toView() {
		WebView webView = new WebView(context);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.loadData(
				"<html><body>" + toString() + "</body></html>",
				"text/html", "utf-8");

		return webView;
	}

}
