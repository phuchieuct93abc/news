package com.feed;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Feed implements Serializable {
    public static String isReadPreferences = "ISREAD";
    String id;
    String title;
    String content;
    String link;
    String image;
    String listId;
    String contentHTML;
    String contentUrl;
    String sourceName;
    private int width;
    private int height;

    public Feed(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("ContentID");
            this.title = jsonObject.getString("Title");
            this.content = jsonObject.getString("Description");
            this.link = jsonObject.getString("BaomoiUrl");
            this.image = jsonObject.getString("LandscapeAvatar");
            this.listId = jsonObject.getString("ListId");
            this.sourceName = jsonObject.getString("SourceName");
            this.contentUrl = jsonObject.getString("ContentUrl");
            this.setHeight(jsonObject.getInt("LandscapeAvatarHeight"));
            this.setWidth(jsonObject.getInt("LandscapeAvatarWidth"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getContentHTML() {
        String result = this.contentHTML;
        result = "<h3>{HEADER}</h3>".replace("{HEADER}", getTitle()) + result;
        result = result.replaceAll("src=\"_\"", "style=\"width: 100%;height:auto\"");
        result = result.replaceAll("data-img-", "");
        result += "<style>" +
                "body{background-color:#EEEEEE}" +
                "p { text-indent: 50px;overflow: hidden;}" +
                "img{margin-left:-50px}</style>";

        return result;
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

    public Boolean isRead(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(this.id, false);
        } catch (Exception e) {
            return false;
        }
    }

    public void setIsRead(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getId(), true).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
