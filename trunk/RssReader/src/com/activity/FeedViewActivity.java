package com.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.phuchieu.news.R;

@EActivity(R.layout.view_swipe)
public class FeedViewActivity extends FragmentActivity {

	@Extra("selectedLink")
	String link;
	@Extra("linkCategory")
	String linkCategory;
	
	PagerAdapter mDemoCollectionPagerAdapter;
	@ViewById
	ViewPager pager;

	@AfterViews
	void run() {

		mDemoCollectionPagerAdapter = new PagerAdapter(getSupportFragmentManager());

		mDemoCollectionPagerAdapter.setContext(this);
		mDemoCollectionPagerAdapter.setLink(link);
		pager.setAdapter(mDemoCollectionPagerAdapter);
	}

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class PagerAdapter extends FragmentStatePagerAdapter {
	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}
	String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	Context context;

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new FeedViewFragment_();
		((FeedViewFragment) fragment).setContext(this.context);
		((FeedViewFragment) fragment).setLink(this.link);
		return fragment;
	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "OBJECT " + (position + 1);
	}
}



