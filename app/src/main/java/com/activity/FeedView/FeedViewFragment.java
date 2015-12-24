package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.config.SharePreference;
import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;

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
    @ViewById
    ImageView imageView;
    @ViewById
    TextView title;
    @Bean
    HttpService httpService;


    Feed feed;
    Boolean darkBackground;
    JavascriptInterface javascriptInterface;
    Boolean isWebviewLoaded = false;


    @Bean
    FeedService feedService;

    public ImageView getShareImageView() {
        return imageView;
    }


    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @UiThread
    void initializeSetting() {
        httpService.loadImage(feed.getImage(), imageView);

        title.setText(feed.getTitle());

//        title.setTextColor(android.R.color.black);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        settings.setAppCacheEnabled(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);
        webView.getSettings().setUserAgentString("Android");
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                //Toast.makeText(TableContentsWithDisplay.this, "url "+url, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                isWebviewLoaded = true;
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                super.onReceivedSslError(view, handler, error);
                //Toast.makeText(TableContentsWithDisplay.this, "error "+error, Toast.LENGTH_SHORT).show();

            }
        });
        javascriptInterface = new JavascriptInterface(webView, getActivity().getApplicationContext(), getActivity());
        webView.addJavascriptInterface(javascriptInterface, "JsHandler1");
        webView.loadUrl("file:///android_asset/index.html");

    }

    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            Log.d("hieu", contentHTML+"///AAA");
            while (!isWebviewLoaded) {

            }
            javascriptInterface.addContentData(contentHTML);
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
        int start = 0, stop = 0;
        int postion = 0;
        for (int index = link.indexOf("/"); index >= 0; index = link.indexOf("/", index + 1)) {
            postion++;
            if (postion == 4) {
                start = index;
            }
            if (postion == 5) {
                stop = index;
            }
        }

        if (link.indexOf("cnet.com") == -1) {
            String stringNeedTobeReplace = link.substring(start, stop + 1);

            link = link.replace(stringNeedTobeReplace, "/c/").replace("www", "m");
        }

        webView.loadUrl(link);


    }



    @AfterViews
    void bindLinkToView() {
        Context context = getActivity().getApplicationContext();
        darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);
        initializeSetting();

        runBackground();


    }

}
