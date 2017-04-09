package com.feed;

import com.FeedGetter;
import com.google.gson.Gson;
import com.model.Articlelist;
import com.model.Feed;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;

import java.util.ArrayList;
import java.util.List;


public class AllFeedGetter extends FeedGetter {
    @Bean
    HttpService httpService;
    @Override
    public List<Feed> getFeed() {
        return null;
    }

    @Override
    public List<Feed> getMore() {
        List<Feed> result = new ArrayList<>();
        String link = getCategoryURLWithIndex();
        try {
            String responseCategory = httpService.readUrl(link);
            Articlelist articlelist = new Gson().fromJson(responseCategory, Articlelist.class);
            if (responseCategory != null) {
                for (Feed feed : articlelist.getArticlelist()) {
                   /* if (feedFilter(feed)) {
                        result.add(feed);
                    } else {
                      //  duplicateCount++;
                    }*/
                }
              //  addDataToList(result);
            }
        }catch (Exception e){


        }
        return result;
    }

    @Override
    public List<Feed> filterFeed(List<Feed> input) {

        return null;
    }


    @Override
    public void reset() {

    }
}
