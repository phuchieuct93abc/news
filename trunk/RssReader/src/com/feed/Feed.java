package com.feed;

public class Feed {
	String title, content, link, image;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
