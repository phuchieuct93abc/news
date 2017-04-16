package com.feed;

import com.FeedGetter;
import com.model.Feed;
import com.orm.query.Condition;
import com.orm.query.Select;

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
        if (this.getFeed().isEmpty()) {

            String categoryId = this.category.getId();
            return Select.from(Feed.class).where(Condition.prop("category").eq(categoryId)).orderBy("id DESC").list();
            //return Feed.find(Feed.class, "category = ?", categoryId);
        } else {
            return new ArrayList<>();
        }

    }


}
