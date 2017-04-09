package com.feed;

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
        return new ArrayList<>();
    }



}
