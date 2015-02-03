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
    public static Categori KHCN = new Categori(ZONE_LIST_TYPE, 53);
    public static Categori TINMOI = new Categori(ZONE_LIST_TYPE, 71);
    public static Categori THEGIOI = new Categori(ZONE_LIST_TYPE, 119);
    public static Categori KINHTE = new Categori(ZONE_LIST_TYPE, 45);
    public static Categori PHAPLUAT = new Categori(ZONE_LIST_TYPE, 58);
    public static Categori THETHAO = new Categori(ZONE_LIST_TYPE, 55);
    public static Categori XAHOI = new Categori(ZONE_LIST_TYPE, 121);
    public static Categori GIAITRI = new Categori(ZONE_LIST_TYPE, 52);
    public static String SPECIAL_LIST_TYPE = "specialzone";
    public static Categori TINNONG = new Categori(SPECIAL_LIST_TYPE, 2);
    public static Categori TINNANH = new Categori(SPECIAL_LIST_TYPE, 4);
    public static String ZINI_LIST_TYPE = "zini";
    public static Categori ANHDEP = new Categori(ZINI_LIST_TYPE, 4);
    public static Categori ANHVUI = new Categori(ZINI_LIST_TYPE, 3);
    public static String LINK_CATEGORY = "http://dataprovider.touch.baomoi.com/json/articlelist.aspx?start={START_PAGE}&count=10&listType={LIST_TYPE}&listId={LIST_ID}&imageMinSize=300&mode=quickview";
    public static List<Feed> listFeed = new ArrayList<>();
    private static int startPage = -10;
    private static String currentLink;

    public static void setListId(Categori categori) {
        currentLink = LINK_CATEGORY.replace("{LIST_ID}", categori.getId() + "");
        currentLink = currentLink.replace("{LIST_TYPE}", categori.getType());
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
        Document doc = null;
        try {
            doc = Jsoup.connect(urlString).timeout(10000).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.select("body").text();
    }

    public static List<Feed> getListFeedFromCategory() {
        try {
            startPage += 10;
            String link = currentLink.replace("{START_PAGE}", "" + startPage);
            Log.i("hieu", link);

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
            return new ArrayList<>();
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

