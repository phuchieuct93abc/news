package com.activity.ListFeedView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.content.Content;
import com.content.TextContent;
import com.feed.FeedContent;
import com.feed.NoteViewAdapter;
import com.phuchieu.news.R;
import com.services.FeedService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.view)
public class FeedViewFragment extends Fragment {
    String link;
    @ViewById
    TextView title;
    @ViewById
    LinearLayout layout;

    FeedContent feedContent;
    String contentHTML, html;
    NoteViewAdapter noteViewAdapter;
    Context context;
    int textSize;

    public void setTitle(TextView title) {
        this.title = title;
    }
    public void setTextSizePref(int textSize){
        this.textSize = textSize;

    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setTextSize(int textSize){
        for(int i=0;i<= layout.getChildCount();i++){
            View view = layout.getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextSize(textSize);
            }
        }
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Background
    void runBackground() {
        try {
            feedContent = FeedService.getFeedContent(link);
            Document doc = Jsoup.parseBodyFragment(feedContent.getContentHTML());
            List<Content> contents = FeedService.parseContent(doc, context);
            setHTML(contents);
        } catch (Exception e) {
        Log.e("hieu",e.getMessage());
        e.printStackTrace();
            List<Content> contents = new ArrayList<Content>();
            TextContent t = new TextContent("Cannot get content", context);
            contents.add(t);
            setHTML(contents);

        }
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
        title.setText(spanString);
    }

    private void addContent(Content content) {
        content.setTextSize(textSize);
        View view = content.toView();
        layout.addView(view);
    }

    @AfterViews
    void bindLinkToView() {
        runBackground();
    }


}
