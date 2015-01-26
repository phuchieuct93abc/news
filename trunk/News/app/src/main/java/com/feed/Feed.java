package com.feed;

import com.services.FeedService;

public class Feed {
    String id;
    String title;
    String content;
    String link;
    String image;
    String listId;
    String contentHTML;


    public String getContentHTML() {
        return contentHTML;
    }

    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public Feed(String id,String listId,String title, String content, String link, String image) {
        this.id=id;
        this.listId = listId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.image = image;
    }

    public Boolean isRead() {
        return FeedService.isRead(this.link);
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
