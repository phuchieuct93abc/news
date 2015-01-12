package com.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.main_activity)
public class MainActivity extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        updateAdaper();
    }

    @ViewById
    SuperListview listView;

    @Bean
    FeedListAdapter adapter;

    private int numberOfPage = 1;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;

/*    @Override
    protected void onResume() {
        super.onResume();
    }*/


    public void updateAdaper()
    {
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
                background();
            }
        });

        listView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                numberOfPage++;
                loadNextPage();
            }

        }, 1);



    }

    @Background
    void loadNextPage() {

        String nextLink = FeedService.getLinkByPageNumber(link, numberOfPage);
        List<Feed> rssItems = FeedService.getFeedFromUrl(nextLink);

        adapter.setListDataMore(rssItems);
       updateList();
    }

    @UiThread
    void updateList() {
        adapter.notifyDataSetChanged();
        listView.hideMoreProgress();
    }

    @ItemClick
    public void listViewItemClicked(Feed clickedItem) {
        FeedViewActivity_.intent(context)
                .extra("selectedLink", clickedItem.getLink())
                .extra("linkCategory", link).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();

    }

    public void setLink(String link) {
        this.link = link;
    }

    //    @Extra
    String link;

    @UiThread
    void run() {
        listView.setAdapter(adapter);
    }

    @Background
    void background() {
        try {
            List<Feed> rssItems = FeedService.getFeedFromUrl(link);
            adapter.setListData(rssItems);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
