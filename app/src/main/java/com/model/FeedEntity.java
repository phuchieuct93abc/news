package com.model;

import com.orm.SugarRecord;

/**
 * Created by ACER on 10/9/2016.
 */

public class FeedEntity extends SugarRecord {
    private Long id;
    private String contentId;
    private String contentHtml;
    private Boolean isRead;

    public FeedEntity(Long id, String contentId, String contentHtml, Boolean isRead) {
        this.id = id;
        this.contentId = contentId;
        this.contentHtml = contentHtml;
        this.isRead = isRead;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
