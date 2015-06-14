package com.feed;

public class FeedContent {
    String title, summary, content;

    public FeedContent(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
