package com.services;
import java.util.List;

import org.jsoup.nodes.Document;

import android.content.Context;

import com.content.Content;
import com.feed.FeedContent;
import com.shirwa.simplistic_rss.RssItem;

public interface FeedServiceInterface {
List<RssItem> getFeed(String source);

FeedContent getFeedContent(String url);

List<Content> parseContent(Document doc, Context context);
}
