package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.services.HttpService;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EFragment
public class FeedViewActivity extends Fragment implements OnPageChangeListener {
    MainActivityInterface mainActivityInterface;
    @Bean
    FeedService feedService;
    @Bean
    CategoryService categoryService;
    @Bean
    HttpService httpService;
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
    String feedID;
    Feed currentFeed;
    ImageView shareElement;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mainActivityInterface = (MainActivityInterface) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.view_swipe, container, false);
        String contentId = getArguments().getInt("feedId") + "";
        shareElement = (ImageView) inflate.findViewById(R.id.shareElement);
        shareElement.setTransitionName(contentId);

        Feed item = categoryService.getFeedById(contentId);
        httpService.loadImage(item.getLandscapeAvatar(), shareElement, null);

        return inflate;

    }

    @Background
    public void loadMoreData() {
        try {
            List<Feed> moreData = categoryService.getMoreFeed();
            setMoreDataList(moreData);
        } catch (Exception e) {
            Log.e("error", "Can't get more data", e);
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
        selectedId.setIsRead(context);
        pager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.imageView));
        shareElement.setVisibility(View.INVISIBLE);

    }

    @UiThread
    void setSelectedPage(int index) {

        pager.setCurrentItem(index, false);
        mainActivityInterface.feedOnView(categoryService.getListFeed().get(index));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("selectedFeed", currentFeed);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            feedID = ((Feed) savedInstanceState.get("selectedFeed")).getContentID() + "";
        }
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void run() {
        if (feedID == null) {
            feedID = getArguments().getInt("feedId") + "";

        }
        try {
            selectedId = categoryService.getFeedById(feedID);
            currentFeed = selectedId;
            mainActivityInterface.onBackFeedList(currentFeed);
            runUI();
            int index = categoryService.getIndexInCaterogyById(selectedId.getContentID());
            currentIndexOfFeed = index;
            categoryService.getListFeed().get(index).setIsRead(context);
            setDataList(categoryService.getListFeed());
            setBackgroundColor();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    shareElement.setVisibility(View.GONE);

                }
            }, 500);
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

    public void changeColor() {
        List<Fragment> fragments = pagerAdapter.getRegisteredFragment();
        for (Fragment fragment : fragments) {
            ((FeedViewFragment_) fragment).applyColor();
        }

    }

    public void changeTextSize() {
        List<Fragment> fragments = pagerAdapter.getRegisteredFragment();
        for (Fragment fragment : fragments) {
            ((FeedViewFragment_) fragment).applyTextsize();
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

            currentFeed = categoryService.getListFeed().get(position);

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
