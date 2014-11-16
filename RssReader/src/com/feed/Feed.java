package com.feed;

import android.content.Context;
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

