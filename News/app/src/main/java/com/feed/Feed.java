package com.feed;

import android.content.Context;
import android.content.SharedPreferences;

import com.activity.Splash_;

import org.json.JSONException;
import org.json.JSONObject;

public class Feed {
    public static String isReadPreferences = "ISREAD";
    String id;
    String title;
    String content;
    String link;
    String image;
    String listId;
    String contentHTML;

    public Feed(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("ContentID");
            this.title = jsonObject.getString("Title");
            this.content = jsonObject.getString("Description");
            this.link = jsonObject.getString("BaomoiUrl");
            this.image = jsonObject.getString("LandscapeAvatar");
            this.listId = jsonObject.getString("ListId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
        return sharedPreferences.getBoolean(this.id, false);


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
