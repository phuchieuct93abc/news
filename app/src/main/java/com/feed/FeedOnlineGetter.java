package com.feed;

import com.FeedGetter;
import com.google.gson.Gson;
import com.model.Articlelist;
import com.model.Feed;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public abstract class FeedOnlineGetter  extends FeedGetter {
    @Bean
    HttpService httpService;



    @Override
    public List<Feed> getMore() {
        List<Feed> result = new ArrayList<>();
        String link = getCategoryURLWithIndex();
        try {
            String responseCategory = httpService.readUrl(link);
            Articlelist articlelist = new Gson().fromJson(responseCategory, Articlelist.class);
            if (responseCategory != null) {

                result = filterFeed(articlelist.getArticlelist());
                if (result.isEmpty()) {
                    return this.getMore();
                }
            }
        } catch (Exception e) {


        }
        setCategoryForFeed(result);


        return result;
    }

    private void setCategoryForFeed(List<Feed> result) {

        for (Feed feed : result) {
            feed.setCategory(this.category.getId());

        }
    }


    public abstract  List<Feed> filterFeed(List<Feed> input);

}
