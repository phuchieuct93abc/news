package com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.content.Content;
import com.content.TextContent;
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

	private void addContent(Element element,	List<Content>  contentList){
		
		if(element.ownText().length() >0){
			contentList.add(new TextContent(element.ownText()));
		}
		if(element.children().size() !=0){
			for(Element childElement : element.children()){
				addContent(childElement,contentList);
				
				
			}
			
		}
		
	}

	@Override
	public void parseContent(Document doc) {
		List<Content> contentList = new ArrayList<Content>();

		for (Element element : doc.getElementsByTag("body").get(0).children()) {
			addContent(element, contentList);

		}
	}

}
