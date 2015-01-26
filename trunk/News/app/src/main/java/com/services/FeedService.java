package com.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.content.Content;
import com.content.ImageContent;
import com.content.TextContent;
import com.content.Video;
import com.feed.Feed;
import com.feed.FeedContent;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
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
                Log.d("hieu","get from cache"+url);
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

    public static List<String> getListFeedLinkFromCaterogy(String category) {
        List<Feed> feeds = getFeedFromUrl(category);
        List<String> result = new ArrayList<String>();
        for (Feed feed : feeds) {
            result.add(feed.getLink());
        }
        return result;
    }

    public static List<String> getCategoryBaseOnFeed(String category, String link) {
        int page = 0;
        List<String> result = new ArrayList<String>();
        while (true) {
            page++;
            String categoryPage = getLinkByPageNumber(category, page);
            if (getListFeedLinkFromCaterogy(categoryPage).indexOf(link) > -1) {
                result.add(categoryPage);
                result.add(page + "");
                return result;
            }
        }

    }

    public static int getIndexCategoryBaseOnFeed(String category, String link) {
        int page = 0;
        while (true) {
            page++;
            String categoryPage = getLinkByPageNumber(category, page);
            if (getListFeedLinkFromCaterogy(categoryPage).indexOf(link) > -1) {
                return page;

            }
            ;
        }

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

    public static List<Feed> getFeedFromUrl(String source) {
        try {
            List<Feed> feeds = new ArrayList<Feed>();
            List<Element> elements = getFeed(source);
            for (Element element : elements) {
                if (checkAds(element)) {
                    continue;
                }
                String title = element.select(".title").text();
                String content = element.select(".summary").text();
                String link = element.select("a").attr("href");
                String image = element.select("img").attr("src");
                feeds.add(new Feed("id","listid",title, content, link, image));

            }

            return feeds;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Feed>();
        }

    }

    private static boolean checkAds(Element element) {
        return element.hasClass("advertorial");
    }

    //get content fromlink of feed
    public static FeedContent getFeedContent(String url) {
/*        int firtIndex = url.lastIndexOf("/");
        int lastIndex = url.lastIndexOf(".epi");
        String articalId = url.substring(firtIndex+1,lastIndex);
        String request=FeedContentWebservice.replace("{id}",articalId);
        String content = FeedContentService.getFeedContent(request);
        String title =Jsoup.parseBodyFragment(content).select("strong").get(0).text();
        String summary = "summay";*/
        Document doc = getHTMLFromURL(url);
        Log.i("hieu",url);
        doc.select("body");
        String title = doc.select("h1").get(0).text();
        String summary = doc.select(".summary").get(0).text();
        String content = doc.select(".article").get(0).html();

        return new FeedContent(title, summary, content);

    }
    public static List<Content> parseContent(Document doc, Context context) {
        List<Content> contentList = new ArrayList<Content>();
        for (Element element : doc.children()) {
            addContent(element, contentList, context);
        }

        return contentList;

    }

    private static void addContent(Element element, List<Content> contentList, Context context) {
        if (element.ownText().length() > 0) {
            contentList.add(new TextContent(element.ownText(), context));
        }
        if (element.select(">img").size() > 0) {
            contentList.add(new ImageContent(
                    element.select(">img").attr("src"), context));
        }
        if (element.select(">iframe").size() > 0) {
            contentList.add(new Video(element.select(">iframe").outerHtml(),
                    context));

        }
        if (element.children().size() != 0) {
            for (Element childElement : element.children()) {
                addContent(childElement, contentList, context);

            }
        }

    }


    public static String getLinkByPageNumber(String link, int index) {

        int lastIndexOfP = link.lastIndexOf("/p/");
        if (lastIndexOfP == -1) {
            lastIndexOfP = link.lastIndexOf(".epi");
        }
        String linkCategory = link.substring(0, lastIndexOfP);
        return linkCategory + "/p/" + index + ".epi";
    }


    public static List<String> getLinkCategoryFromPageOne(String link) {
        int currentLink = 1;
        int lastIndexOfP = link.lastIndexOf("/p/");
        List<String> result = new ArrayList<String>();
        if (lastIndexOfP != -1) {
            int lastIndexOfEPI = link.lastIndexOf(".epi");
            currentLink = Integer.parseInt(link.substring(lastIndexOfP + 3,
                    lastIndexOfEPI));
            for (int index = 1; index <= currentLink; index++) {
                result.add(getLinkByPageNumber(link, index));
            }
        }

        return result;
    }

}
