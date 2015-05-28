package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.config.SharePreference;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.FeedContentService_JSON;
import com.styles.CssStyles;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment {
    @ViewById
    WebView webView;
    Context context;
    Feed feed;
    Boolean darkBackground;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @UiThread
    void initializeSetting() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient());

    }

    @Background
    void runBackground() {
        initializeSetting();

        String contentHTML;
        try {
            contentHTML = FeedContentService_JSON.getFeedContentFromFeed(feed).getContentHTML();
            setContentToWebview(contentHTML);
        } catch (Exception e) {
            setOriginalURLForWebview();
        }
    }

    @UiThread
    void setOriginalURLForWebview() {
        webView.loadUrl(feed.getLink());
    }

    @UiThread
    void setContentToWebview(String contentHTML) {
        if(darkBackground)contentHTML+= CssStyles.DARK_BACKGROUND;

        webView.loadDataWithBaseURL(null, contentHTML, "text/html", "UTF-8", null);
    }

    @AfterViews
    void bindLinkToView() {
        darkBackground = FeedViewActivity.getSharePreference().getBooleanValue(SharePreference.DARK_BACKGROUND);
        runBackground();

    }

}
