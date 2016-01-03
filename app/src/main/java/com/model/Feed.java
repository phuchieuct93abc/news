package com.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Feed {
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
    public static String isReadPreferences = "ISREAD";


    public Integer getContentID() {
        return ContentID;
    }

    public void setContentID(Integer contentID) {
        ContentID = contentID;
    }

    public Integer getSourceID() {
        return SourceID;
    }

    public void setSourceID(Integer sourceID) {
        SourceID = sourceID;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public Integer getZoneID() {
        return ZoneID;
    }

    public void setZoneID(Integer zoneID) {
        ZoneID = zoneID;
    }

    public String getZoneName() {
        return ZoneName;
    }

    public void setZoneName(String zoneName) {
        ZoneName = zoneName;
    }

    public String getBaomoiUrl() {
        return BaomoiUrl;
    }

    public void setBaomoiUrl(String baomoiUrl) {
        BaomoiUrl = baomoiUrl;
    }

    public String getContentUrl() {
        return ContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        ContentUrl = contentUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getShortBody() {
        return ShortBody;
    }

    public void setShortBody(String shortBody) {
        ShortBody = shortBody;
    }

    public Integer getHasImage() {
        return HasImage;
    }

    public void setHasImage(Integer hasImage) {
        HasImage = hasImage;
    }

    public String getPortraitAvatar() {
        return PortraitAvatar;
    }

    public void setPortraitAvatar(String portraitAvatar) {
        PortraitAvatar = portraitAvatar;
    }

    public Integer getPortraitAvatarWidth() {
        return PortraitAvatarWidth;
    }

    public void setPortraitAvatarWidth(Integer portraitAvatarWidth) {
        PortraitAvatarWidth = portraitAvatarWidth;
    }

    public Integer getPortraitAvatarHeight() {
        return PortraitAvatarHeight;
    }

    public void setPortraitAvatarHeight(Integer portraitAvatarHeight) {
        PortraitAvatarHeight = portraitAvatarHeight;
    }

    public String getLandscapeAvatar() {
        return LandscapeAvatar;
    }

    public void setLandscapeAvatar(String landscapeAvatar) {
        LandscapeAvatar = landscapeAvatar;
    }

    public Integer getLandscapeAvatarWidth() {
        return LandscapeAvatarWidth;
    }

    public void setLandscapeAvatarWidth(Integer landscapeAvatarWidth) {
        LandscapeAvatarWidth = landscapeAvatarWidth;
    }

    public Integer getLandscapeAvatarHeight() {
        return LandscapeAvatarHeight;
    }

    public void setLandscapeAvatarHeight(Integer landscapeAvatarHeight) {
        LandscapeAvatarHeight = landscapeAvatarHeight;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public Integer getListId() {
        return ListId;
    }

    public void setListId(Integer listId) {
        ListId = listId;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

    public String getListType() {
        return ListType;
    }

    public void setListType(String listType) {
        ListType = listType;
    }

    public Integer getComments() {
        return Comments;
    }

    public void setComments(Integer comments) {
        Comments = comments;
    }

    public Double getDate() {
        return Date;
    }

    public void setDate(Double date) {
        Date = date;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public boolean isRead(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(this.getContentID() + "", false);
        } catch (Exception e) {
            return false;
        }
    }

    public void setIsRead(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Feed.isReadPreferences, Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getContentID() + "", true).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentHTML() {
        String result = this.ContentHTML;
        result = "<h3>{HEADER}</h3>".replace("{HEADER}", getTitle()) + result;
        result = result.replaceAll("data-img-src", "src");
        result = result.replaceAll("<p>\u200B</p>", "");
        result = result.replaceAll("src=\"_\"", "");
        Log.d("hieu", "getContentHTML: "+result);

        return result;
    }

    public void setContentHTML(String contentHTML) {
        ContentHTML = contentHTML;
    }
}
