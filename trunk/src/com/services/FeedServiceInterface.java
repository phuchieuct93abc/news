package com.services;
import java.util.List;

import com.shirwa.simplistic_rss.RssItem;

public interface FeedServiceInterface {
List<RssItem> getFeed(String source);

String getResponseFromUrl(String url);
}
