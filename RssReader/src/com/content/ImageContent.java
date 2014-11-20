package com.content;

public class ImageContent extends Content {
	String url;

	public ImageContent(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.url;
	}

}
