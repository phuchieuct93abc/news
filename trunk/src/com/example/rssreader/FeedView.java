package com.example.rssreader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@EActivity(R.layout.list_feed_viewer)
public class FeedView extends Activity {
	@ViewById
	WebView webView;
	@Extra("link")
	String link;
	@AfterViews
	void bindLinkToView(){
		webView.setWebViewClient(new WebViewClient());
	//	webView.loadUrl(link);
		
	}

}
