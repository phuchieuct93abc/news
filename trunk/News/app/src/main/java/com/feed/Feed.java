package com.feed;

import android.content.Context;
import android.content.SharedPreferences;

import com.activity.Splash_;

public class Feed {
    String id;
    String title;
    String content;
    String link;
    String image;
    String listId;
    String contentHTML;
    public static String isReadPreferences = "ISREAD";

    public Feed(String id, String listId, String title, String content, String link, String image) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.image = image;
    }

    public String getContentHTML() {
        return contentHTML;
    }

    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isRead() {
        SharedPreferences sharedPreferences = Splash_.getContext().getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(this.id,false);



    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
