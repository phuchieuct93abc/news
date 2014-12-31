package com.services.main_screen;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;


public class Tile {
	String name, title, imgUrl,url,icon;
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	OnClickListener onClick;
	
	public OnClickListener getOnClick() {
		return onClick;
	}

	public void setOnClick(OnClickListener onClick) {		
		this.onClick = onClick;
	}

	public Tile(String name, String title, String imgUrl, String url,String icon) {
		super();
		this.name = name;
		this.title = title;
		this.imgUrl = imgUrl;
		this.url = url;
		this.icon = icon;
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
