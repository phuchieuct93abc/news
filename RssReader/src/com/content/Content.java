package com.content;

import android.content.Context;
import android.view.View;

public class Content {
	Context context;

	public Content(Context context2) {
		this.context = context2;
	}

	public View toView() {
		return null;
	}

	public boolean isVideo() {
		return false;
	}

}
