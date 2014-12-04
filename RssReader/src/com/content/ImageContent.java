package com.content;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ImageContent extends Content {
	String url;

	public ImageContent(String url,Context context) {
		super(context);
		this.url = url;
	}
	@Override
	public String toString() {
		return this.url;
	}
	public View toView() {
		ImageView imageView = new ImageView(context);
		UrlImageViewHelper.setUrlDrawable(imageView, url);
		return imageView;

	}
}
