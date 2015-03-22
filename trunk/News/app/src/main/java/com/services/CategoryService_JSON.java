package com.services;

import android.util.Log;

import com.feed.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService_JSON {
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
    public static String LINK_CATEGORY = "http://dataprovider.touch.baomoi.com/json/articlelist.aspx?start={START_PAGE}&count=10&listType={LIST_TYPE}&listId={LIST_ID}&imageMinSize=300&mode=quickview";
    public static List<Feed> listFeed = new ArrayList<>();
    private static int startPage = -10;
    private static String currentLink;
    private final static int timeout = 2000;

    public static void setListId(Category category) {
        currentLink = LINK_CATEGORY.replace("{LIST_ID}", category.getId() + "");
        currentLink = currentLink.replace("{LIST_TYPE}", category.getType());
        clearCacheList();

    }

    public static List<Feed> getListFeed() {
        return listFeed;
    }

    public static void setListFeed(List<Feed> listFeed) {
        CategoryService_JSON.listFeed = listFeed;
        startPage = -10;
    }

    public static void clearCacheList() {
        setListFeed(new ArrayList<Feed>());
        startPage = -10;
    }

    public static String readUrl(String urlString) {
        Document doc =null;
        try {
            doc = Jsoup.connect(urlString).timeout(timeout).get();
        } catch (Exception e) {
            try {
                Log.e("hieu","that bat lan 1"+urlString);

                doc = Jsoup.connect(urlString).timeout(timeout).get();
            } catch (IOException e1) {
                Log.e("hieu","that bat lan 1 "+urlString);
            }
        }
        return doc.select("body").text();
    }

    public static List<Feed> getListFeedFromCategory() {
        try {
            startPage += 10;
            String link = currentLink.replace("{START_PAGE}", "" + startPage);
            String responseCategory = readUrl(link);
            JSONObject jObject = new JSONObject(responseCategory);
            JSONArray jArray = jObject.getJSONArray("articlelist");
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    Feed feed = new Feed(oneObject);
                    if (getIndexInCaterogyById(feed.getId()) == -1) {
                        listFeed.add(feed);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return listFeed;
        } catch (Exception e) {
            e.printStackTrace();
            return listFeed;
        }


    }

    public static int getIndexInCaterogyById(String id) {
        for (Feed d : listFeed) {
            if (d.getId().equals(id)) {
                return listFeed.indexOf(d);
            }
        }
        return -1;
    }
}

