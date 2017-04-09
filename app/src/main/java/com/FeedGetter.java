package com;

import com.interfaces.FeedGetterInterfacer;
import com.model.Feed;
import com.model.Source.Category;

import java.util.ArrayList;
import java.util.List;


public abstract class FeedGetter implements FeedGetterInterfacer {
    private Category category;
    private List<Feed> feeds = new ArrayList<>();
    private int duplicateCount =0;

    protected String getCategoryURLWithIndex() {
        String categoryLink = this.category.getLink();

        return categoryLink.replace("{START_PAGE}", "" + (feeds.size() + duplicateCount));


    }
    public void reset(){
        this.feeds.clear();


    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    protected Boolean isDuplicate(List<Feed> input){
       /* for (Feed d : this.feeds) {
            if (d.getContentID().equals(id)) {
                int index = listFeed.indexOf(d);
                return index;
            }
        }*/
        return false;
    }
}
