package com.services;

import java.io.IOException;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.feed.FeedContent;
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

	@Override
	public FeedContent getFeedContent(String url) {
		Document doc;

		try {
			doc = Jsoup.connect(url).get();

			Element title = doc.getElementsByAttributeValue("itemprop",
					"headline").get(0);

			Element summary = doc.getElementsByAttributeValue("itemprop",
					"description").get(0);
			Element content = doc.getElementsByAttributeValue("itemprop",
					"articleBody").get(0);
			return new FeedContent(title, summary, content);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
