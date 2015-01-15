package com.services;

import android.content.Context;

import com.content.Content;
import com.feed.FeedContent;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public interface FeedServiceInterface {
    List<Element> getFeed(String source);

    FeedContent getFeedContent(String url);

    List<Content> parseContent(Document doc, Context context);
}
