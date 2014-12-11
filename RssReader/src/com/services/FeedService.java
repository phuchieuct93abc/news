package com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import com.content.Content;
import com.content.ImageContent;
import com.content.TextContent;
import com.content.Video;
import com.feed.FeedContent;

@EBean
public class FeedService implements FeedServiceInterface {

	@Override
	public List<Element> getFeed(String source) {
		try {

			Document doc = Jsoup.connect(source).timeout(10000).get();
			Elements elements = doc.select(".story");
			return elements;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FeedContent getFeedContent(String url) {
		Document doc;

		try {

			doc = Jsoup.connect(url).timeout(10000).get();
			Element title = doc.select("title").get(0);
			Element summary = doc.select(".summary").get(0);
			Element content = doc.select(".article").get(0);
			return new FeedContent(title, summary, content);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addContent(Element element, List<Content> contentList,
			Context context) {
		if (element.ownText().length() > 0) {
			contentList.add(new TextContent(element.ownText(), context));
		}
		if (element.select(">img").size() > 0) {
			contentList.add(new ImageContent(
					element.select(">img").attr("src"), context));
		}
		if (element.select(">iframe").size() > 0) {
			contentList.add(new Video(element.select(">iframe")
					.outerHtml(), context));
			
			
		}
		if (element.children().size() != 0) {
			for (Element childElement : element.children()) {
				addContent(childElement, contentList, context);

			}
		}
	}

	@Override
	public List<Content> parseContent(Document doc, Context context) {
		List<Content> contentList = new ArrayList<Content>();

		for (Element element : doc.getElementsByTag("body").get(0).children()) {
			addContent(element, contentList, context);
		}

		return contentList;

	}

}
