package com.feed;

import org.jsoup.nodes.Element;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.shirwa.simplistic_rss.RssItem;

public class Feed {
String title,content,link,image;
public String getLink() {
	return link;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public void setLink(String link) {
	this.link = link;
}
public Feed(String title, String content, String link, String image) {
	this.title = title;
	this.content = content;
	this.link = link;
	this.image = image;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Feed(RssItem feed) {
	this.title = feed.getTitle();
	this.content = feed.getDescription();
	this.link=feed.getLink();
	this.image = feed.getEnclosure();
}
public Feed(Element element) {	
	this.title = element.select(".title").text();
	this.content = element.select(".summary").text();
	this.link= element.select("a").attr("href");
	this.image = element.select("img").attr("src");

}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}
}

class NoClickWebView extends WebView {
    public NoClickWebView(Context context) {
        super(context);
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }
}

