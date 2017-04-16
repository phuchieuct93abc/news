package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.activity.MainActivityInterface;
import com.config.Config_;
import com.model.Feed;
import com.nineoldandroids.view.ViewHelper;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;
import com.services.ImageGetter;
import com.services.UtilService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

@EFragment
public class FeedViewFragment extends Fragment {
    public static final int MAX_OVERSCROLL = -130;
    public static final int MAX_TOP_OVERSCROLL = 130;
    @ViewById
    TextView textViewContent;
    @ViewById
    ImageView imageView;
    @ViewById
    TextView title;
    @ViewById
    ProgressBar progressBar;
    @Bean
    HttpService httpService;

    @Pref
    Config_ myPrefs;

    @ViewById
    ConstraintLayout constrainLayout;
    @ViewById
    ProgressBar progress_bar;
    @ViewById
    TextView time;
    @ViewById
    TextView zoneName;
    @ViewById
    ImageView logo;
    @ViewById
    TextView txtDate;
    @ViewById
    ScrollView scrollView;
    @ViewById
    RelativeLayout wrapper;
    @ViewById
    ImageView closeIcon;
    @ViewById
    TextView closeText;
    @ViewById
    ConstraintLayout reloadWrapper;

    @ViewById
    ImageView topCloseIcon;
    @ViewById
    TextView topCloseText;
    @ViewById
    ConstraintLayout topReloadWrapper;
    @ViewById
    com.example.component.CustomVideoPlayer customVideoPlayer;
    @Bean
    UtilService utilService;

    Feed feed;
    @Bean
    FeedService feedService;
    MainActivityInterface mainActivityInterface;
    Boolean backToListFeed = false;
    ViewTreeObserver viewTreeObserver;
    Boolean isLoadedContent = false;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("feed", feed);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            feed = (Feed) savedInstanceState.get("feed");
        }
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view, container, false);
        view.findViewById(R.id.imageView).setTransitionName(feed.getContentID() + "");
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.imageView).setTransitionName(feed.getContentID() + "");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mainActivityInterface = (MainActivityInterface) context;
        }
    }

    @UiThread
    void initializeSetting() {
        httpService.loadImage(feed.getLandscapeAvatar(), imageView, progressBar);
        title.setText(feed.getTitle());
        zoneName.setText(feed.getZoneName());
        feed.getDate();
        feedService.getIconOfUrl(feed.getContentUrl(), logo);
        String formatted = getFormattedDate();
        txtDate.setText(formatted);


    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    private String getFormattedDate() {
        Long valueFromClient = new Double(feed.getDate()).longValue();
        Date date = new Date(valueFromClient);
        return new SimpleDateFormat("dd-MM HH:mm").format(date);
    }


    @Background
    void loadContent() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            updateTextViewContent(contentHTML);

        } catch (Exception e) {
            Log.e("ERROR", "Can't get content", e);
            getFailed();


        } finally {
            isLoadedContent = true;
        }
    }

    @UiThread
    void updateTextViewContent(String html) {
        ImageGetter imageGetter = new ImageGetter(getActivity(), textViewContent);
        Spanned spanned = Html.fromHtml(html, imageGetter, null);
        if (spanned != null && textViewContent != null) {
            textViewContent.setText(spanned);
            progress_bar.setVisibility(View.GONE);

        }
        setVideo(html);
    }


    @UiThread
    void getFailed() {
        textViewContent.setText("Can't connect to server");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLoadedContent) {

            loadContent();
        } else {


        }
    }

    @AfterViews
    void afterView() {
        applyColor();
        applyTextsize();
        initializeSetting();
        ActivityCompat.startPostponedEnterTransition(getActivity());
        scrollUpToClose();


    }


    private void setVideo(String htmlString) {


        try {
            String videoUrl = utilService.getVideo(htmlString);
            //   videoUrl = "http://baomoi-video.r.za.zdn.vn/af2ae754ef4e7926fda0dc65d772b326/58ca57d4/video.viettimes.vn/2017_03_15/lemai/cnngoihanoilacainoicuadisan1489479819_1.mp4";

            customVideoPlayer.setUrl(videoUrl);

            customVideoPlayer.ready();
        } catch (Exception e) {
            Log.e("ERROR", "Can load video", e);
        }


    }

    private void scrollUpToClose() {
        viewTreeObserver = scrollView.getViewTreeObserver();
        viewTreeObserver.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
                    viewTreeObserver = scrollView.getViewTreeObserver();
                }


                if (scrollView != null) {
                    int scrollY = scrollView.getScrollY(); // For ScrollView
                    ViewHelper.setTranslationY(imageView, scrollY / 2);
                } else {

                    viewTreeObserver.removeOnScrollChangedListener(this);
                }


            }
        });
        IOverScrollDecor iOverScrollDecor = OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!backToListFeed) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                backToListFeed = true;
            }
        };
        final Handler handler = new Handler();
        iOverScrollDecor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {

                if (state == STATE_DRAG_END_SIDE) {
                    updateBottomCloseState(offset);
                }
                if (state == STATE_DRAG_START_SIDE) {
                    updateTopCloseState(offset);


                }
                if (state == STATE_BOUNCE_BACK && (offset < MAX_OVERSCROLL || offset > MAX_TOP_OVERSCROLL)) {
                    handler.postDelayed(runnable, 0);
                }
                if (state == STATE_BOUNCE_BACK) {
                    updateCloseWrapper(offset);
                }

            }
        });
    }

    private void updateCloseWrapper(float offsetY) {
        int size = Math.round(Math.abs(offsetY));
        if (offsetY > 0) {
            ViewGroup.LayoutParams layoutParamsTop = topReloadWrapper.getLayoutParams();
            layoutParamsTop.height = size;
            topReloadWrapper.setLayoutParams(layoutParamsTop);

        } else {

            ViewGroup.LayoutParams layoutParams = reloadWrapper.getLayoutParams();
            layoutParams.height = size;
            reloadWrapper.setLayoutParams(layoutParams);
        }


    }

    private void updateBottomCloseState(float offsetY) {
        updateCloseWrapper(offsetY);

        Boolean blackColor = myPrefs.darkBackground().get();

        boolean isRefresh = offsetY < MAX_OVERSCROLL;


        if (isRefresh) {
            if (blackColor) {
                closeIcon.setImageResource(R.drawable.ic_close_white_24dp);

            } else {
                closeIcon.setImageResource(R.drawable.ic_close_black_24dp);

            }
            closeText.setText(R.string.Release_To_Close);
        } else {
            if (blackColor) {
                closeIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);

            } else {
                closeIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

            }
            closeText.setText(R.string.Pull_To_Close);

        }
    }

    private void updateTopCloseState(float offsetY) {
        updateCloseWrapper(offsetY);

        Boolean blackColor = myPrefs.darkBackground().get();
        boolean isRefresh = offsetY > MAX_TOP_OVERSCROLL;


        if (isRefresh) {
            if (blackColor) {
                topCloseIcon.setImageResource(R.drawable.ic_close_white_24dp);

            } else {
                topCloseIcon.setImageResource(R.drawable.ic_close_black_24dp);

            }
            topCloseText.setText(R.string.Release_To_Close);
        } else {
            if (blackColor) {
                topCloseIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);

            } else {
                topCloseIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

            }
            topCloseText.setText(R.string.Pull_down_to_close);

        }
    }


    public void applyColor() {
        Boolean blackColor = myPrefs.darkBackground().get();
        Context context = getContext();

        int textPrimaryColor, textSecondaryColor, backgroundColor;
        if (blackColor) {
            textPrimaryColor = ContextCompat.getColor(context, R.color.text_primary_dark);
            textSecondaryColor = ContextCompat.getColor(context, R.color.text_secondary_dark);
            backgroundColor = ContextCompat.getColor(context, R.color.background_dark);

        } else {
            textPrimaryColor = ContextCompat.getColor(context, R.color.text_primary_light);
            textSecondaryColor = ContextCompat.getColor(context, R.color.text_secondary_light);
            backgroundColor = ContextCompat.getColor(context, R.color.background_light);
        }

        title.setTextColor(textPrimaryColor);
        textViewContent.setTextColor(textPrimaryColor);
        zoneName.setTextColor(textSecondaryColor);
        txtDate.setTextColor(textSecondaryColor);

        constrainLayout.setBackgroundColor(backgroundColor);

        progress_bar.setBackgroundColor(backgroundColor);
        wrapper.setBackgroundColor(backgroundColor);
        closeText.setTextColor(textPrimaryColor);
        topCloseText.setTextColor(textPrimaryColor);

    }

    public void applyTextsize() {
        int textSize = myPrefs.textSize().get();
        textViewContent.setTextSize((float) textSize * 5 + 14);

    }


}
