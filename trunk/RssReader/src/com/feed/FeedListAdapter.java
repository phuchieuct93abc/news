package com.feed;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@EBean
public class FeedListAdapter extends BaseAdapter {

	List<Feed> feeds = new ArrayList<Feed>();

	@RootContext
	Context context;

	public void setData(Element data) {
		feeds.add(new Feed(data));

	}

	public void setDataFromSearch(Feed feed) {
		feeds.add(feed);

	}
	private boolean checkAds(Element element){
		return element.hasClass("advertorial");
	}

	public void setListData(List<Element> listData){
		for(Element element : listData){
			if(checkAds(element)){
				continue;
			}
			setData(element);
		}
	}

	public void clear() {
		feeds = new ArrayList<Feed>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		FeedItemView FeedItemView;
		if (convertView == null) {
			FeedItemView = FeedItemView_.build(context);
		} else {
			FeedItemView = (FeedItemView) convertView;
		}

		FeedItemView.bindDataToView(getItem(position));

		return FeedItemView;
	}

	@Override
	public int getCount() {
		return feeds.size();
	}

	@Override
	public Feed getItem(int position) {
		return feeds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}