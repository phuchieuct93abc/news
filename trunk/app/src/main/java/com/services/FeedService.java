package com.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

@EBean
public class FeedService {
    static Context context;
    static String sharedPreferencesCaterogy = "CATEROGY_CACHE";
    static String sharedPreferencesFeed = "FEED_CACHE";
    static String sharedPreferencesReadFeed = "READ_FEED_CACHE";
    static String FeedContentWebservice = "http://dataprovider.touch.baomoi.com/json/article.aspx?articleId={id}";

    private static Boolean isCategoryLink(String url) {
        return url.indexOf("/p/") > -1;
    }

    public static Boolean isRead(String feedLink) {
        SharedPreferences prefs = getContext().getSharedPreferences(
                FeedService.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        Boolean result = prefs.getBoolean(feedLink, false);
        return result;
    }

    public static void setRead(String feedLink) {
        SharedPreferences prefs = getContext().getSharedPreferences(
                FeedService.sharedPreferencesReadFeed, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(feedLink, true).commit();


    }

    public static void clearCache() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                FeedService.sharedPreferencesCaterogy, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        FeedService.context = context;
    }

    private static Document getHTMLFromURL(String url) {
        try {
            SharedPreferences prefs;
            if (isCategoryLink(url)) {
                prefs = getContext().getSharedPreferences(
                        FeedService.sharedPreferencesCaterogy,
                        Context.MODE_PRIVATE);


            } else {
                prefs = getContext()
                        .getSharedPreferences(
                                FeedService.sharedPreferencesFeed,
                                Context.MODE_PRIVATE);
            }
            String result = prefs.getString(url, "");
            if (result.equals("")) {
                Document doc;
                try {
                    doc = getDataFromURLAndSetToCache(url, prefs);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return doc;
            } else {
                Log.d("hieu", "get from cache" + url);
                return Jsoup.parse(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Document getDataFromURLAndSetToCache(String url,
                                                        SharedPreferences prefs) throws IOException {
        Document doc = Jsoup.connect(url).timeout(5000).get();
        Editor editor = prefs.edit();
        editor.putString(url, doc.html());
        editor.commit();

        return doc;
    }


    //Get content from link of category
    private static List<Element> getFeed(String source) {
        try {

            Document doc = getHTMLFromURL(source);

            Elements elements = doc.select(".story");
            return elements;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
