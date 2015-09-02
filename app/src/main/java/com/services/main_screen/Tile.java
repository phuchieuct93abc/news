package com.services.main_screen;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.feed.Category;


public class Tile {
    String name;
    String title;
    String imgUrl;
    String icon;
    Category caterogi;
    OnClickListener onClick;
    private String type;

    public Tile(String name, String title, String imgUrl, Category caterogi, String icon, String type) {
        super();
        this.name = name;
        this.title = title;
        this.imgUrl = imgUrl;
        this.icon = icon;
        this.caterogi = caterogi;
        this.type = type;
    }

    public Category getCaterogi() {
        return caterogi;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public OnClickListener getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClickListener onClick) {
        this.onClick = onClick;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
