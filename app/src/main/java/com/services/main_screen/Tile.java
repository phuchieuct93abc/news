package com.services.main_screen;

import android.view.View.OnClickListener;

import com.model.Category;


public class Tile {
    String name;
    String title;
    String imgUrl;
    String icon;
    Category caterogi;
    OnClickListener onClick;

    public Tile(String name, String title, String imgUrl, Category caterogi, String icon) {
        super();
        this.name = name;
        this.title = title;
        this.imgUrl = imgUrl;
        this.icon = icon;
        this.caterogi = caterogi;

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


}
