package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.activity.FragmentEnum;
import com.activity.MainActivityInterface;
import com.config.Config_;
import com.config.SharePreference;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EFragment(R.layout.view_swipe)
public class FeedViewActivity extends Fragment implements OnPageChangeListener {
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
    @Pref
    Config_ config;


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

        pager.addOnPageChangeListener(this);
        setSelectedPage(categoryService.getIndexInCaterogyById(selectedId.getContentID()));
        feedService.setRead(selectedId.getContentID() + "");
    }

    @UiThread
    void setSelectedPage(int index) {

        pager.setCurrentItem(index, false);
        mainActivityInterface.feedOnView(categoryService.getListFeed().get(index));

    }

    @AfterViews
    void run() {

        try {
            String feedID = getArguments().getInt("feedId") + "";
            selectedId = categoryService.getFeedById(feedID);
            runUI();
            int index = categoryService.getIndexInCaterogyById(selectedId.getContentID());
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
    public void changeColor(){
        List<Fragment> fragments = pagerAdapter.getRegisteredFragment();

        for(Fragment fragment:fragments){



            ((FeedViewFragment_)fragment).applyColor();



        }

    }
    public void changeTextSize(){
        List<Fragment> fragments = pagerAdapter.getRegisteredFragment();

        for(Fragment fragment:fragments){



            ((FeedViewFragment_)fragment).applyTextsize();



        }

    }
    @Override
    public void onStart() {
        super.onStart();
        mainActivityInterface.setRunningFragment(FragmentEnum.FEED);
        mainActivityInterface.changeColor(new Runnable() {
            @Override
            public void run() {
                changeColor();
            }
        });
        mainActivityInterface.changeTextSize(new Runnable() {
            @Override
            public void run() {
                changeTextSize();
            }
        });


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {

            Feed currentFeed = categoryService.getListFeed().get(position);

            currentIndexOfFeed = position;
            mainActivityInterface.onBackFeedList(currentFeed);


            currentFeed.setIsRead(context);
            mainActivityInterface.feedOnView(categoryService.getListFeed().get(position));
            indexOfFragment = position;
            if (position == pagerAdapter.getCount() - 1) {
                loadMoreData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
