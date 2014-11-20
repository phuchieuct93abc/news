package com.content;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.services.DownloadImageTask;

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
	@Override
	public View toView() {
		ImageView imageView = new ImageView(context);
		new DownloadImageTask(imageView)
				.execute(url);
		return imageView;

	}
}
