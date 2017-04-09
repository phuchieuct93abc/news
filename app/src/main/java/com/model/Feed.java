package com.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Feed extends SugarRecord implements Serializable {


    public static String isReadPreferences = "ISREAD";
    private Integer ContentID;
    private Integer SourceID;
    private String SourceName;
    private Integer ZoneID;
    private String ZoneName;
    private String BaomoiUrl;
    private String ContentUrl;
    private String Title;
    private String Description;
    private String ShortBody;
    private Integer HasImage;
    private String PortraitAvatar;
    private Integer PortraitAvatarWidth;
    private Integer PortraitAvatarHeight;
    private String LandscapeAvatar;
    private Integer LandscapeAvatarWidth;
    private Integer LandscapeAvatarHeight;
    private String Images;
    private Integer ListId;
    private String ListName;
    private String ListType;
    private Integer Comments;
    private Double Date;
    private Boolean isRead;
    private String ContentHTML;

    public Integer getContentID() {
        return ContentID;
    }


    public String getSourceName() {
        return SourceName;
    }


    public String getZoneName() {
        return ZoneName;
    }


    public String getContentUrl() {
        return ContentUrl;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getLandscapeAvatar() {
        return LandscapeAvatar;
    }


    public Double getDate() {
        return Date;
    }


    public boolean isRead(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
        boolean isReadBoolean = sharedPreferences.getBoolean(String.valueOf(getContentID()), false);
        return isReadBoolean;
    }

    public void setIsRead(Context context) {
        try {
            isRead = true;
            SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);

            sharedPreferences.edit().putBoolean(String.valueOf(getContentID()), true).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentHTML() {
        if (this.ContentHTML == null) {
            return null;
        }
        String result = this.ContentHTML;
        result = result.replaceAll("data-img-src", "src");
        result = result.replaceAll("<p>\u200B</p>", "");
        result = result.replaceAll("src=\"_\"", "");

        return result;
    }

    public void setContentHTML(String contentHTML) {
        ContentHTML = contentHTML;
    }

}
