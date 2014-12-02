package com.services.main_screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rssreader.R;

public class TileView extends LinearLayout {
String name,url;
TextView title;
	public String getName() {
	return name;
}

public void setName(String name) {
    title.setText(name);
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

	public TileView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    inflater.inflate(R.layout.tile_view, this, true);
			    LinearLayout linearLayout =  (LinearLayout) getChildAt(0);
			    
			    title = (TextView)  linearLayout.getChildAt(0);
			    title.setText(name);
		// TODO Auto-generated constructor stub
	}

	

}
