package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.FeedContentService_JSON;

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

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = FeedContentService_JSON.getFeedContentFromFeed(feed).getContentHTML();
            setContentToWebview(contentHTML);

        } catch (Exception e) {
            try {

                setOriginalURLForWebview();
            } catch (Exception e1) {
                contentHTML = "Cannot get content";
            }
        }
    }

    @UiThread
    void setOriginalURLForWebview() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(feed.getLink());
    }

    @UiThread
    void setContentToWebview(String contentHTML) {
        contentHTML = "<h3>{HEADER}</h3>".replace("{HEADER}", feed.getTitle()) + contentHTML;
        contentHTML = contentHTML.replaceAll("src=\"_\"", "style=\"width: 100%;height:auto\"");
        contentHTML = contentHTML.replaceAll("data-img-", "");
        contentHTML += "<style>body{background-color:#EEEEEE}p { text-indent: 50px;}img{margin-left:-50px}p:nth-last-child(2){text-indent: 0em;}</style>";
        contentHTML += "<a href='{LINK}'>Source</a>".replace("{LINK}", feed.getContentUrl());
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        webView.loadDataWithBaseURL(null, contentHTML, "text/html", "UTF-8", null);
    }

    @AfterViews
    void bindLinkToView() {
        runBackground();
    }
}
