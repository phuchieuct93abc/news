package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.phuchieu.news.R;
import com.services.FeedService;

@EActivity(R.layout.view_swipe)
public class FeedViewActivity extends FragmentActivity {

	@Bean
	FeedService feedService;
	@Extra("selectedLink")
	String link;
	@Extra("linkCategory")
	String linkCategory;

	PagerAdapter mDemoCollectionPagerAdapter;
	@ViewById
	ViewPager pager;
	List<String> listFeedLink;

	@Background
	void runBackground() {
		this.listFeedLink = feedService.getListFeedFromCaterogy(linkCategory);
runUI();	}

	@UiThread
	void runUI() {
		mDemoCollectionPagerAdapter = new PagerAdapter(	getSupportFragmentManager());
		mDemoCollectionPagerAdapter.setContext(this);
		mDemoCollectionPagerAdapter.setLink(link);
		mDemoCollectionPagerAdapter.setCategoryLink(this.listFeedLink);
		pager.setAdapter(mDemoCollectionPagerAdapter);
	}

	@AfterInject
	void run() {
		runBackground();
	}

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class PagerAdapter extends FragmentStatePagerAdapter {

	FeedService feedService = new FeedService();
	String link;
	List<String> categoryLink;

	public List<String> getCategoryLink() {
		return categoryLink;
	}



	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}



	public void setCategoryLink(List<String> categoryLink) {
		this.categoryLink = categoryLink;
	}

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
		Log.i("hieu",getCategoryLink().get(i));
		((FeedViewFragment) fragment).setLink(getCategoryLink().get(i));
		
		//((FeedViewFragment) fragment).setLink(this.link);

		return fragment;
	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "NEWS " + (position + 1);
	}
}
