package com.feed;

public class FeedContent {
	String title, summary, content;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public FeedContent(String title, String summary, String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return this.title;
	}
}
