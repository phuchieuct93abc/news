package com;

import android.util.Log;

import com.interfaces.FeedGetterInterfacer;
import com.model.Feed;
import com.model.Source.Category;

import java.util.ArrayList;
import java.util.List;


public abstract class FeedGetter implements FeedGetterInterfacer {
    protected Category category;
    private List<Feed> feeds = new ArrayList<>();
    protected int duplicateCount =0;

    protected String getCategoryURLWithIndex() {
        String categoryLink = this.category.getLink();
        Log.d("Get feed process",String.format("Get from %s, duplicate :%s",feeds.size() + duplicateCount,duplicateCount));
        return categoryLink.replace("{START_PAGE}", "" + (feeds.size() + duplicateCount));


    }
    public void reset(){
        this.feeds = new ArrayList<>();
        duplicateCount = 0;

    }

    public List<Feed> getFeed(){

        return this.feeds;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    protected Boolean isDuplicate(Feed input){
       for (Feed d : this.feeds) {
            if (d.getContentID().equals(input.getContentID())) {
                return true;
            }
        }
        return false;
    }
    public Feed getFeedById(String id) {
        for (Feed d : this.feeds) {
            if (d.getContentID().equals(Integer.valueOf(id))) {
                return d;
            }
        }
        return null;
    }
    public int getIndexInCaterogyById(Integer id) {
        for (Feed d : this.feeds) {
            if (d.getContentID().equals(id)) {
                return this.feeds.indexOf(d);
            }
        }
        return -1;
    }

    public List<Feed> getMoreFeed() {
        List<Feed> moreFeed = this.getMore();
        this.feeds.addAll(moreFeed);

        return moreFeed;
    }
}
