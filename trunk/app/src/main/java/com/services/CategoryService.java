package com.services;

import android.content.Context;
import android.util.Log;
import com.feed.Category;
import com.feed.Feed;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class CategoryService {
    @Bean
    HttpService httpService;
    @RootContext
    Context context;

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
    private static String currentLink;
    private int duplicateCount = 0;

    public void setListId(Category category) {
        currentLink = LINK_CATEGORY.replace("{LIST_ID}", category.getId() + "");
        currentLink = currentLink.replace("{LIST_TYPE}", category.getType());
        clearCacheList();
    }

    public List<Feed> getListFeed() {
        return listFeed;
    }

    private void setListFeed(List<Feed> listFeed) {
        CategoryService.listFeed = listFeed;
    }

    public void clearCacheList() {
        duplicateCount = 0;
        setListFeed(new ArrayList<Feed>());
    }


    public List<Feed> getMoreFeed() {
        List<Feed> result = new ArrayList<>();
        try {
            int beforeUpdateLength = listFeed.size();
            if (beforeUpdateLength >= 190) return listFeed;
            String link = currentLink.replace("{START_PAGE}", "" + (listFeed.size() + duplicateCount));
            String responseCategory = httpService.readUrl(link);
            if (responseCategory != null) {
                JSONObject jObject = new JSONObject(responseCategory);
                JSONArray jArray = jObject.getJSONArray("articlelist");
                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        Feed feed = new Feed(oneObject);
                        if (getIndexInCaterogyById(feed) == -1) {
                            result.add(feed);
                        } else {
                            duplicateCount++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addDataToList(result);
            }

            return result;

        } catch (Exception e) {
            Log.e("hieu", e.toString());
            return new ArrayList<>();
        }


    }

    private void addDataToList(List<Feed> addedData) {

        listFeed.addAll(addedData);
        Log.e("hieu", "size" + listFeed.size() + "");
    }

    public int getIndexInCaterogyById(Feed item) {
        for (Feed d : listFeed) {
            if (d.getId().equals(item.getId())) {
                int index = listFeed.indexOf(d);
                return index;
            }
        }
        return -1;
    }
}

