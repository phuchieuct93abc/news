package com.activity.FeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.content.Content;
import com.content.TextContent;
import com.feed.Feed;
import com.feed.FeedContent;
import com.phuchieu.news.R;
import com.services.FeedContentService_JSON;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment {
    String link;

    @ViewById
    WebView webView;

    FeedContent feedContent;
    Context context;
    int textSize;
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

    public void setLink(String link) {
        this.link = link;
    }

    @Background
    void runBackground() {
        try {
/*            feedContent = FeedService.getFeedContent(link);
            Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
            List<Content> contents = FeedService.parseContent(doc, context);
            setHTML(contents);*/
//            this.feed = CategoryService_JSON.getListFeed().get(0);
            String contentHTML = FeedContentService_JSON.getFeedContentFromFeed(feed).getContentHTML();
            setContentToWebview(contentHTML);
        } catch (Exception e) {
            Log.e("hieu", e.getMessage());
            e.printStackTrace();
            List<Content> contents = new ArrayList<>();
            TextContent t = new TextContent("Cannot get content", context);
            contents.add(t);
            setHTML(contents);

        }
    }

    @UiThread
    void setContentToWebview(String contentHTML) {
        contentHTML = "<h3>{HEADER}</h3>".replace("{HEADER}", feed.getTitle()) + contentHTML;
        contentHTML = contentHTML.replaceAll("src=\"_\"", "style=\"width: 100%;height:auto\"");
        contentHTML = contentHTML.replaceAll("data-img-", "");
        contentHTML = contentHTML + "<style>body{background-color:#EEEEEE}p { text-indent: 50px;}img{margin-left:-50px}p:nth-last-child(2){text-indent: 0em;}</style>";

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(22);
        webView.loadDataWithBaseURL(null, contentHTML, "text/html", "UTF-8", null);
    }

    @UiThread
    void setHTML(List<Content> contents) {
        try {
            for (Content content : contents) {
                addContent(content);
            }
            String feedTitle = feedContent.getTitle();
            setTitle(feedTitle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void setTitle(String feedTitle) {
        String tempString = feedTitle;
        SpannableString spanString = new SpannableString(tempString);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
    }

    private void addContent(Content content) {
        content.setTextSize(textSize);
        View view = content.toView();
    }

    @AfterViews
    void bindLinkToView() {
        runBackground();
    }


}
