package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
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
		linkCategory = FeedService.getIndexOfFeedInCategory(linkCategory, link);
		Log.i("hieu", linkCategory);
		this.listFeedLink = FeedService
				.getListFeedLinkFromCaterogy(linkCategory);
		runUI();
	}

	@Background
	public void loadMoreData(int arg0) {
		if (arg0 == 0) {
			/*page = 3;
			getMoreDateFromPageFromStart();
			setSelectedPage(13);*/

		}
		if (arg0 == listFeedLink.size() - 2) {
			page++;
			getMoreDateFromPageFromEnd();
		}
	}

	private void getMoreDateFromPageFromStart() {
		getMoreDateFromPage(true);
	}

	private void getMoreDateFromPageFromEnd() {
		getMoreDateFromPage(false);
	}

	private void getMoreDateFromPage(Boolean fromStart) {
		String nextLink = FeedService.getLinkByPageNumber(linkCategory, page);
		List<Feed> feeds = FeedService.getFeedFromUrl(nextLink);
		if (fromStart) {
			for (Feed feed : feeds) {
				String feedLink = feed.getLink();
				listFeedLink.add(0, feedLink);
			}
		} else {

		}
		for (Feed feed : feeds) {
			String feedLink = feed.getLink();
			listFeedLink.add(feedLink);
		}

		mDemoCollectionPagerAdapter.setListLink(listFeedLink);
		updateAdapter();
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

		OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

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
		};
		pager.setOnPageChangeListener(onPageChangeListener);
		if (listFeedLink.indexOf(link) == -1) {

		}

		setSelectedPage(listFeedLink.indexOf(link));

	}

	@UiThread
	void setSelectedPage(int index) {
		pager.setCurrentItem(index);
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
		return fragment;
	}

	@Override
	public int getCount() {
		return listLink.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String feedLink = getListLink().get(position);
		// String title = FeedService.getFeedContent(feedLink).getTitle();
		String title = "NEWS " + listLink.size();
		return title;
	}
}
