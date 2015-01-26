package com.feed;

import com.services.FeedService;

public class Feed {
    String title, content, link, image;
    String id;

    public Feed(String id,String title, String content, String link, String image) {
        this.id=id;
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
