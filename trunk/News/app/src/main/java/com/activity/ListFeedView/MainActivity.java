package com.activity.ListFeedView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.activity.FeedView.FeedViewActivity_;
import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.services.CategoryService_JSON;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.activity_main)
public class MainActivity extends Fragment {

    public static final int REQUEST_CODE = 2;
    @ViewById
    SuperListview listView;
    @Bean
    FeedListAdapter adapter;
    Context context;
    //    @Extra
    String link;
    private int numberOfPage = 1;

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();

    }

    @AfterViews
    void afterView() {
        background();
        setOnScrollListener();
    }

    private void setOnScrollListener() {

        listView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FeedService.clearCache();
                CategoryService_JSON.clearCacheList();
                background();
            }
        });

        listView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                Log.i("hieu", "load next page");
                numberOfPage++;
                loadNextPage();
            }

        }, 1);


    }

    @Background
    void loadNextPage() {
        try {
            // List<Feed> rssItems = FeedService.getFeedFromUrl(nextLink);
            Log.i("hieu", "load more");
            List<Feed> rssItems = CategoryService_JSON.getListFeedFromCategory();
            Log.i("hieu", rssItems.size() + "");
            adapter.setListDataMore(rssItems);
        } catch (Exception e) {
            Log.e("hieu", "cannot get more ");
            e.printStackTrace();
        } finally {
            updateList();

        }

    }

    @UiThread
    void updateList() {
        adapter.notifyDataSetChanged();
        listView.hideMoreProgress();
    }

    @ItemClick
    public void listViewItemClicked(Feed clickedItem) {
        Intent i = new Intent(getActivity(), FeedViewActivity_.class);
        i.putExtra("selectedId", clickedItem.getId());
        i.putExtra("linkCategory", link);

        startActivityForResult(i, 2);



       /* FeedViewActivity_.intent(context)
                .extra("selectedId", clickedItem.getId())
                .extra("linkCategory", categoryId).flags(Intent.FLAG_ACTIVITY_NEW_TASK).startForResult(REQUEST_CODE);*/

    }


    public void setLink(String link) {
        this.link = link;
    }

    @UiThread
    void run() {
        listView.setAdapter(adapter);
    }

    @Background
    void background() {
        try {
//            List<Feed> rssItems = FeedService.getFeedFromUrl(categoryId);

            List<Feed> rssItems = CategoryService_JSON.getListFeedFromCategory();

            adapter.setListData(rssItems);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
