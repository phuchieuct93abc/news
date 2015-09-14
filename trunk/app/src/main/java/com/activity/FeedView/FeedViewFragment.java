package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.config.SharePreference;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.styles.CssStyles;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment {
    @ViewById
    WebView webView;
    Feed feed;
    Boolean darkBackground;
    @Bean
    FeedService feedService;
    @ViewById
    BootstrapButton openSource;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @UiThread
    void initializeSetting() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);

        webView.setWebViewClient(new WebViewClient());
    }

    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            setContentToWebview(contentHTML);
        } catch (Exception e) {
            try {
                setOriginalURLForWebview();

            } catch (Exception ex) {
                Log.d("set url for webview", ex.getMessage());
            }
        }finally {
            setButtonVisible();
        }
    }
    @UiThread
    void setButtonVisible(){
        openSource.setVisibility(View.VISIBLE);

    }

    @UiThread
    void setOriginalURLForWebview() {
        webView.loadUrl(feed.getLink());


    }

    @UiThread
    void setContentToWebview(String contentHTML) {
        if (darkBackground) contentHTML += CssStyles.DARK_BACKGROUND;

        webView.loadDataWithBaseURL(null, contentHTML, "text/html", "UTF-8", null);
    }

    @AfterViews
    void bindLinkToView() {
        Context context = getActivity().getApplicationContext();
        darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);
        initializeSetting();
        runBackground();
        openSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(feed.getContentUrl()));
                startActivity(i);

            }
        });

    }

}
