package com.feed;

import org.jsoup.nodes.Element;

public class FeedContent {
    Element title, summary, content;

    public FeedContent(Element title, Element summary, Element content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getSummary() {
        return summary.text();
    }

    public String getSummaryHTML() {
        return summary.html();
    }

    public String getTitle() {
        return title.text();
    }

    public String getTitleHTML() {
        return title.html();
    }

    public String getContent() {
        return content.text();
    }

    public String getContentHTML() {
        return content.html();
    }


}
