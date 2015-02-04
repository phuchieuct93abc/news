package com.activity;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.feed.Feed;
import com.feed.FeedListAdapter;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@EActivity(R.layout.search_screen)
public class SearchScreen extends Activity {
    @ViewById
    Button button1;
    @ViewById
    ListView listView;
    @Bean
    FeedListAdapter adapter;
    @ViewById
    EditText SearchText;
    List<org.jsoup.nodes.Element> feeds;

    /*
     * @TextChange void SearchTextTextChanged(TextView hello, CharSequence text)
     * { performSearch(text.toString()); }
     */
    private Timer timer = new Timer();

    @AfterViews
    void init() {
        performSearch("Smart phone");
        setDelaySearchText();
    }

    @AfterTextChange(R.id.SearchText)
    void afterTextChange(Editable arg0) {

        timer.cancel();
        timer = new Timer();
        long DELAY = 500;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                performSearch(SearchText.getText().toString());
            }

        }, DELAY);
    }


    private void setDelaySearchText() {
        SearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    @Background
    void performSearch(String key) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void uIThread() {
        listView.setAdapter(adapter);
    }

    @ItemClick
    public void listViewItemClicked(Feed clickedItem) {
        String link = clickedItem.getLink();
        link = link.replace("http://www", "http://m");
        //FeedView_.intent(this).link(link).start();

    }

}
