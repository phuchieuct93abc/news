package com.activity.FeedView;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by phuchieuct on 5/6/2015.
 */
public class JavascriptInterface {

    Context context;
    String TAG = "JsHandler";
    WebView webView;
    Activity activity;


    public JavascriptInterface(WebView _webView, Context context, Activity activity) {
        webView = _webView;
        this.context = context;
        this.activity = activity;
    }

    @android.webkit.JavascriptInterface
    public void onLoaded() {
        javaFnCall("01234959839");
    }

    @android.webkit.JavascriptInterface
    public void toast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

    }

    public void javaFnCall(String jsString) {

        final String webUrl = "javascript:setToNumber('" + jsString + "')";
        webView.loadUrl(webUrl);


    }

    public void addContentData(String contentData) {
        final String webUrl = "javascript:addContent('" + contentData + "')";
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if (!activity.isFinishing())
            // loadurl on UI main thread
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });

    }
}