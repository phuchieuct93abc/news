package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.config.SharePreference;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment implements Html.ImageGetter {
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


    Feed feed;
    Boolean darkBackground;
    JavascriptInterface javascriptInterface;
    Boolean isWebviewLoaded = false;


    @Bean
    FeedService feedService;
    private String TAG = "FeedViewFragment";


    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }


    @UiThread
    void initializeSetting(){
        httpService.loadImage(feed.getLandscapeAvatar(), imageView,progressBar);
        title.setText(feed.getTitle());



    }


    @Background
    void runBackground() {
        String contentHTML;
        try {
            contentHTML = feedService.getFeedContentFromFeed(feed).getContentHTML();
            updateTextViewContent(contentHTML);
        } catch (Exception e) {
            try {
                setOriginalURLForWebview();

            } catch (Exception ex) {
            }
        }
    }
    @UiThread
    void updateTextViewContent(String html){

        Log.d(TAG, "updateTextViewContent() called with: " + "html = [" + html + "]");
        Spanned spanned = Html.fromHtml(html,this, null);
        textViewContent.setText(spanned);
    }


    @UiThread
    void setOriginalURLForWebview() {
        textViewContent.setText("Can't connect to server");
        //webView.loadUrl(feed.getContentUrl());


    }



    @AfterInject
    void bindLinkToView() {
        Context context = getActivity().getApplicationContext();
        darkBackground = new SharePreference(context).getBooleanValue(SharePreference.DARK_BACKGROUND);

        initializeSetting();

        runBackground();


    }


    @Override
    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        Log.d(TAG, "getDrawable() called with: " + "s = [" + s + "]");
        try{

            Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
            d.addLevel(0, 0, empty);
            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
            Point size = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(size);
            new LoadImage(textViewContent,getActivity().getApplicationContext(),size).execute(s, d);
        }catch(Exception e){
            Log.e(TAG, "getDrawable: faild", e);
        }


        return d;
    }
}
