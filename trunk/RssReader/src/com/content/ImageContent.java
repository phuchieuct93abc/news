package com.content;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.hipmob.gifanimationdrawable.GifAnimationDrawable;
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
			// imageView.setLayoutParams(new
			// ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			AnimationDrawable drawable = new GifAnimationDrawable(context
					.getResources().openRawResource(R.raw.anim1));
			drawable.setOneShot(true);
			imageView.setImageDrawable(drawable);
			UrlImageViewHelper.setUrlDrawable(imageView, toString(),R.drawable.u93ka80a);

			return imageView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
