package com.services;
import java.util.List;

import com.feed.FeedContent;
import com.shirwa.simplistic_rss.RssItem;

public interface FeedServiceInterface {
List<RssItem> getFeed(String source);

FeedContent getFeedContent(String url);
}
