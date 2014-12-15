package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.feed.Feed;
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
	int page = 1;

	@Background
	void runBackground() {
		this.listFeedLink = feedService.getListFeedFromCaterogy(linkCategory);
		runUI();
	}

	@Background
	public void loadMoreData(int arg0) {
		if (arg0 == listFeedLink.size() - 2) {
			page++;
			String nextLink = feedService.nextLink(linkCategory, page);
			List<Feed> feeds = feedService.getFeedFromUrl(nextLink);
			for (Feed feed : feeds) {
				String feedLink = feed.getLink();
				listFeedLink.add(feedLink);
			}
			mDemoCollectionPagerAdapter.setListLink(listFeedLink);
			updateAdapter();
		}
	}

	@UiThread
	public void updateAdapter() {
		mDemoCollectionPagerAdapter.notifyDataSetChanged();
		
	}

	@UiThread
	void runUI() {
		mDemoCollectionPagerAdapter = new PagerAdapter(
				getSupportFragmentManager());
		mDemoCollectionPagerAdapter.setContext(this);
		mDemoCollectionPagerAdapter.setLink(link);
		mDemoCollectionPagerAdapter.setListLink(listFeedLink);
		pager.setAdapter(mDemoCollectionPagerAdapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {


			@Override
			public void onPageSelected(int arg0) {
				loadMoreData(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		if(listFeedLink.indexOf(link) == -1){
			
		}
		
		pager.setCurrentItem(listFeedLink.indexOf(link));
		Log.i("hieu","curernt "+listFeedLink.indexOf(link) +"  "+listFeedLink.size());
	}

	@AfterViews
	void run() {
		runBackground();
	}

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class PagerAdapter extends FragmentStatePagerAdapter {

	FeedService feedService = new FeedService();
	String link;
	List<String> listLink;

	public void addMoreListLink(String link) {
		listLink.add(link);
	}

	public List<String> getListLink() {
		return listLink;
	}

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setListLink(List<String> categoryLink) {
		this.listLink = categoryLink;
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
		((FeedViewFragment) fragment).setLink(getListLink().get(i));

		// ((FeedViewFragment) fragment).setLink(this.link);

		return fragment;
	}

	@Override
	public int getCount() {
		return listLink.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "NEWS " + (position + 1);
	}
}