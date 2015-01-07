package com.content;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.phuchieu.news.R;

public class ImageContent extends Content {
	String url;
	Context context;

	public ImageContent(String url, Context context) {
		super(context);
		this.url = url;
		this.context = context;
	}

	@Override
	public String toString() {
		return this.url;
	}

	public View toView() {
		try {
			ImageView imageView = new ImageView(context);
			 imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setAdjustViewBounds(true);

			 UrlImageViewHelper.setUrlDrawable(imageView, toString(), R.drawable.loading);


			return imageView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
