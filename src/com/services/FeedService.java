package com.services;

import java.util.List;

import org.androidannotations.annotations.EBean;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;
@EBean
public class FeedService implements FeedServiceInterface {

	@Override
	public List<RssItem> getFeed(String source) {
		try {
			RssReader rssReader = new RssReader(source);

			List<RssItem> rssItems = rssReader.getItems();
			return rssItems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
