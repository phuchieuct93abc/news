package com.model.Source;

import java.util.List;

/**
 * Created by lphieu on 10/18/2016.
 */

public class Source {
    private String language;
    private List<Category> categories;
    private String detail;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
