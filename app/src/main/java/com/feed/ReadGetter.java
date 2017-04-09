package com.feed;

import android.util.Log;

import com.FeedGetter;
import com.model.Feed;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuchieuPC on 4/9/2017.
 */
@EBean
public class ReadGetter extends FeedGetter {

    @Override
    public List<Feed> getMore() {
        Log.d("Get more", this.getFeed().size() + "");
        if (this.getFeed().isEmpty()) {

            String categoryId = this.category.getId();

            return Feed.find(Feed.class, "category = ?", categoryId);
        } else {
            return new ArrayList<>();
        }

    }


}
