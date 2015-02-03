package com.services;

import android.util.Log;
import android.widget.Toast;

import com.activity.Splash;
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
    private static int startPage = -10;

    public static int KHCN = 53;
    public static int THEGIOI = 71;
    public static String ZONE_LIST_TYPE="zone";
    public static String SPECIAL_LIST_TYPE="specialzone";


    public static String LINK_CATEGORY = "http://dataprovider.touch.baomoi.com/json/articlelist.aspx?start={START_PAGE}&count=10&listType={LIST_TYPE}&listId={LIST_ID}&imageMinSize=300&mode=quickview";

    public static List<Feed> listFeed = new ArrayList<>();

    private static String currentLink;
    public static void setListId(int id,String listType){
        currentLink = LINK_CATEGORY.replace("{LIST_ID}", id + "");
        currentLink = currentLink.replace("{LIST_TYPE}", listType);
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
            Log.i("hieu",link );

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
