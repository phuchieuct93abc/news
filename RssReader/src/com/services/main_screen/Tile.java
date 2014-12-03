package com.services.main_screen;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class Tile {
	String name, title, imgUrl,url;
	OnClickListener onClick;
	
	public OnClickListener getOnClick() {
		return onClick;
	}

	public void setOnClick(OnClickListener onClick) {		
		this.onClick = onClick;
	}

	public Tile(String name, String title, String imgUrl, String url) {
		super();
		this.name = name;
		this.title = title;
		this.imgUrl = imgUrl;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public View getView(Context context) {
		TileView tileView = new TileView(context);
		tileView.setTile(this);
		return tileView;
	}
}
