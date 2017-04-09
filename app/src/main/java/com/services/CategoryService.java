package com.services;

import android.content.Context;

import com.FeedGetter;
import com.builder.FeedGetterBuilder;
import com.config.Config_;
import com.google.gson.Gson;
import com.model.Category;
import com.model.Feed;
import com.model.Source.Source;
import com.model.Source.Sources;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class CategoryService {
    public static String ZONE_LIST_TYPE = "zone";
    public static Category KHCN = new Category(ZONE_LIST_TYPE, 53);
    public static Category TINMOI = new Category(ZONE_LIST_TYPE, 71);
    public static Category THEGIOI = new Category(ZONE_LIST_TYPE, 119);
    public static Category KINHTE = new Category(ZONE_LIST_TYPE, 45);
    public static Category PHAPLUAT = new Category(ZONE_LIST_TYPE, 58);
    public static Category THETHAO = new Category(ZONE_LIST_TYPE, 55);
    public static Category XAHOI = new Category(ZONE_LIST_TYPE, 121);
    public static Category GIAITRI = new Category(ZONE_LIST_TYPE, 52);
    public static String SPECIAL_LIST_TYPE = "specialzone";
    public static Category TINNONG = new Category(SPECIAL_LIST_TYPE, 2);
    public static Category TINNANH = new Category(SPECIAL_LIST_TYPE, 4);
    public static String ZINI_LIST_TYPE = "zini";
    public static Category ANHDEP = new Category(ZINI_LIST_TYPE, 4);
    public static Category ANHVUI = new Category(ZINI_LIST_TYPE, 3);
    public static Category CNET = new Category("CNET", 1);
    public static String LINK_CATEGORY = "http://dataprovider.touch.baomoi.com/json/articlelist.aspx?start={START_PAGE}&count=10&listType={LIST_TYPE}&listId={LIST_ID}&imageMinSize=300&mode=quickview";
    public static List<Feed> listFeed = new ArrayList<>();
    @Bean
    HttpService httpService;
    @RootContext
    Context context;
    @Bean
    FeedService feedService;
    @Pref
    Config_ config;
    @Bean
    FeedGetterBuilder feedGetterBuilder;

    FeedGetter feedGetter;
    com.model.Source.Category selectedCategory;


    @AfterInject
    public void initialize(){
        feedGetter = feedGetterBuilder.getFeedGetter();
    }


    public Source getSourceByLanguage(String language) {
        String configString = loadJSONFromAsset();
        Sources sources = new Gson().fromJson(configString, Sources.class);
        for (Source source : sources.getSources()) {
            if (language.equals(source.getLanguage())) {

                return source;
            }
        }
        return null;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getAssets().open("sources.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    public void setSelectedCategory(com.model.Source.Category category) {
        feedGetter.reset();
        feedGetter.setCategory(category);
        this.selectedCategory = category;
    }

    public List<Feed> getListFeed() {
        return feedGetter.getFeed();
    }


    public void setListFeed(List<Feed> listFeed) {
        this.listFeed = listFeed;
    }

    public void clearCacheList() {
        initialize();
        feedGetter.reset();
        feedGetter.setCategory(this.selectedCategory);
    }



    public List<Feed> getMoreFeed() throws Exception {
       return feedGetter.getMore();
    }







    public int getIndexInCaterogyById(Integer id) {
       return feedGetter.getIndexInCaterogyById(id);
    }

    public Feed getFeedById(String id) {
       return feedGetter.getFeedById(id);
    }


}

