package com.services;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.model.Articlelist;
import com.model.Category;
import com.model.Feed;
import com.model.Item;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

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
    public static String CNET_CATEROGY = "http://feed.cnet.com/feed/river?limit=10&start={START_PAGE}&edition=us&locale=us&version=3_0&platform=android&release=3.1.5";
    public static List<Feed> listFeed = new ArrayList<>();
    @Bean
    HttpService httpService;
    @RootContext
    Context context;
    private String currentLink;
    private int duplicateCount = 0;

    public void setListId(Category category) {
        String newLink = LINK_CATEGORY.replace("{LIST_ID}", category.getId() + "");
        newLink = newLink.replace("{LIST_TYPE}", category.getType());
        if (!newLink.equals(currentLink)) {
            clearCacheList();
        }
        currentLink = newLink;

    }

    public List<Feed> getListFeed() {
        return listFeed;
    }


    public void setListFeed(List<Feed> listFeed) {
        this.listFeed = listFeed;
    }

    public void clearCacheList() {
        duplicateCount = 0;
        setListFeed(new ArrayList<Feed>());
    }

    private String getCategoryURLWithIndex() {

        return currentLink.replace("{START_PAGE}", "" + (listFeed.size() + duplicateCount));


    }

    public List<Feed> getMoreFeed() throws Exception {
        List<Feed> result = new ArrayList<>();
        int beforeUpdateLength = listFeed.size();

        if (beforeUpdateLength >= 190) {
            Toast.makeText(context, "Reached maximum number 190 feeds", Toast.LENGTH_SHORT).show();
            return listFeed;
        }
        String link = getCategoryURLWithIndex();
        String responseCategory = httpService.readUrl(link);
        Articlelist articlelist = new Gson().fromJson(responseCategory, Articlelist.class);
        if (responseCategory != null) {
            for (Feed feed : articlelist.getArticlelist()) {
                if (getIndexInCaterogyById(feed.getContentID()) == -1) {
                    Item t = new Item(feed.getTitle());
                    t.save();


                    result.add(feed);
                } else {
                    duplicateCount++;
                }
            }
            addDataToList(result);
        }
        return result;
    }

    private void addDataToList(List<Feed> addedData) {
        listFeed = new ArrayList<>(listFeed);
        listFeed.addAll(addedData);
    }

    public int getIndexInCaterogyById(Integer id) {
        for (Feed d : listFeed) {
            if (d.getContentID().equals(id)) {
                int index = listFeed.indexOf(d);
                return index;
            }
        }
        return -1;
    }

    public Feed getFeedById(String id) {
        for (Feed d : listFeed) {
            if (d.getContentID().equals(Integer.valueOf(id))) {
                return d;
            }
        }
        return null;
    }
}

