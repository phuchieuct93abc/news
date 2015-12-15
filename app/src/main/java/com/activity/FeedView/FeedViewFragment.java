package com.activity.FeedView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

@EFragment
public class FeedViewFragment extends Fragment {
    @ViewById
    WebView webView;
    Feed feed;
    Boolean darkBackground;


    @Bean
    FeedService feedService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.view, container, false);

    }

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
        webView.getSettings().setUserAgentString("Android");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
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
        }
    }


    @UiThread
    void setOriginalURLForWebview() {
        String link = feed.getLink();
        int start =0,stop=0;
        int postion=0;
        for (int index = link.indexOf("/");  index >= 0; index = link.indexOf("/", index + 1))
        { postion++;
            if(postion ==4){start=index;}
            if(postion==5){stop=index;}
        }

        if(link.indexOf("cnet.com")==-1){
            String stringNeedTobeReplace = link.substring(start,stop+1);

          link = link.replace(stringNeedTobeReplace,"/c/").replace("www","m");
        }

        webView.loadUrl(link);


    }

    @UiThread
    void setContentToWebview(String contentHTML) {
        if (darkBackground) contentHTML += CssStyles.DARK_BACKGROUND;
        contentHTML="<body style='padding-top:10px'>"+contentHTML+"</body>";

    /*    contentHTML = "<script src=\"file:///android_asset/bLazy.js\" type=\"text/javascript\"></script>\n"+contentHTML;
        contentHTML = "<script src=\"file:///android_asset/jquery.js\" type=\"text/javascript\"></script>\n"+contentHTML;
        contentHTML = "<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">\n\n"+contentHTML;*/
        Log.d("hieu",contentHTML);
        webView.loadDataWithBaseURL("file:///android_asset/", contentHTML, "text/html", "UTF-8", null);
    }

    @AfterViews
    void bindLinkToView() {
        Context context = getActivity().getApplicationContext();
        darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);
        initializeSetting();
        
        runBackground();


    }

}
