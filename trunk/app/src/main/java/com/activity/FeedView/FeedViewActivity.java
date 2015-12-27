package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.activity.MainActivityInterface;
import com.config.SharePreference;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.view_swipe)
public class FeedViewActivity extends Fragment {
    MainActivityInterface mainActivityInterface;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    @ViewById
    ImageView imageView;


    Feed selectedId;
    PagerAdapter pagerAdapter;
    @ViewById
    ViewPager pager;
    @ViewById
    RelativeLayout viewSwipe;
    int currentIndexOfFeed;
    int indexOfFragment;
    Context context;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mainActivityInterface = (MainActivityInterface) activity;

    }

    @Background
    public void loadMoreData() {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();
            setMoreDataList(moreData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @UiThread
    public void setDataList(List<Feed> feeds)

    {
        pagerAdapter.setData(feeds);
    }

    @UiThread
    public void setMoreDataList(List<Feed> feeds)

    {
        pagerAdapter.setMoreData(feeds);
    }

    @UiThread
    void runUI() {

        pagerAdapter = new PagerAdapter(getFragmentManager());

        pagerAdapter.setData(categoryService.getListFeed());
        pagerAdapter.setItem(selectedId);
        pager.setAdapter(pagerAdapter);

        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                try {
                    Feed currentFeed = categoryService.getListFeed().get(arg0);

                    currentIndexOfFeed = arg0;
                    mainActivityInterface.onBackFeedList(currentFeed);


                    currentFeed.setIsRead(context);
                    Log.d("page selected hieu", arg0 + "");
                    mainActivityInterface.feedOnView(categoryService.getListFeed().get(arg0));
                    indexOfFragment = arg0;
                    if (arg0 == pagerAdapter.getCount() - 1) {
                        loadMoreData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        pager.addOnPageChangeListener(onPageChangeListener);
        setSelectedPage(categoryService.getIndexInCaterogyById(selectedId.getId()));
        feedService.setRead(selectedId.getId());
    }

    @UiThread
    void setSelectedPage(int index) {

        pager.setCurrentItem(index, false);
        mainActivityInterface.feedOnView(categoryService.getListFeed().get(index));

    }

    @AfterViews
    void run() {

        try {
            String feedID = getArguments().getString("feedId");
            selectedId = categoryService.getFeedById(feedID);
            runUI();
            int index = categoryService.getIndexInCaterogyById(selectedId.getId());
            currentIndexOfFeed = index;
            categoryService.getListFeed().get(index).setIsRead(context);
            setDataList(categoryService.getListFeed());


            setBackgroundColor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBackgroundColor() {
        Boolean darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);
        if (darkBackground) {
            viewSwipe.setBackgroundColor(Color.parseColor("#23282A"));
        }
    }


}
