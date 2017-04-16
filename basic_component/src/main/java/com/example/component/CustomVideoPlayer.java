package com.example.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class CustomVideoPlayer extends RelativeLayout {
    String url;
    View rootView;
    Context ctx;
    WebView videoWebview;


    public CustomVideoPlayer(Context context) {
        super(context);
        this.ctx = context;
        init(context);
    }

    public CustomVideoPlayer(Context context, AttributeSet attrs) {

        super(context,attrs);
        this.ctx = context;
        init(context);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        Log.d("de", url);
        this.url = url;
    }
    public void ready(){
        if(this.url!=null){
            rootView.setVisibility(View.VISIBLE);
            videoWebview.loadUrl(url);
        }
    }

    public void resume() {
        videoWebview.onResume();

    }

    public void pause() {
        videoWebview.onPause();

    }


    private void init(Context context) {
        rootView = inflate(context, R.layout.custom_component, this);
        videoWebview = (WebView) rootView.findViewById(R.id.videoWebview);
        videoWebview.setWebViewClient(new WebViewClient());
        videoWebview.getSettings().setJavaScriptEnabled(true);
        videoWebview.getSettings().setMediaPlaybackRequiresUserGesture(true);
        videoWebview.setWebChromeClient(new WebChromeClient());
        rootView.setVisibility(View.GONE);
    }


}
