package com.services;

import android.util.Log;

import com.feed.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoryService_JSON {
    public static String LINK_CATEGORY = "http://dataprovider.touch.baomoi.com/json/articlelist.aspx?start={START_PAGE}&count=10&listType=zone&listId=53&imageMinSize=300&mode=quickview";

    public static void setListFeed(List<Feed> listFeed) {
        CategoryService_JSON.listFeed = listFeed;
    }

    public static List<Feed> getListFeed() {
        return listFeed;
    }

    public static List<Feed> listFeed = new ArrayList<>();
    private static int startPage=-10;

    public static String readUrl(String urlString) {
        Document doc = null;
        try {
        Log.i("hieu",urlString);
            doc = Jsoup.connect(urlString).timeout(10000).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.select("body").text();
    }

    public static List<Feed> getListFeedFromCategory(String caterogyLink) {
        try {
            startPage+=10;
            caterogyLink = caterogyLink.replace("{START_PAGE}",""+startPage);

            String responseCategory = readUrl(caterogyLink);

            JSONObject jObject = new JSONObject(responseCategory);
            JSONArray jArray = jObject.getJSONArray("articlelist");
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String id = oneObject.getString("ContentID");
                    String title = oneObject.getString("Title");
                    String description = oneObject.getString("Description");
                    String url =  oneObject.getString("BaomoiUrl");
                    String image =  oneObject.getString("LandscapeAvatar");
                    String listId =  oneObject.getString("ListId");
                    Feed feed = new Feed(id,listId,title, description, url, image);
                    listFeed.add(feed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i("hieu",listFeed.size()+"");
            return listFeed;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }


    }
}
