package com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.activity.FeedView.FeedViewActivity_;
import com.activity.fragment_activity.CaterogyFragment_;
import com.activity.fragment_activity.ListFeedFragment;
import com.activity.fragment_activity.ListFeedFragment_;
import com.model.Feed;
import com.phuchieu.news.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main2)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    final static String CATEGORY_FRAGMENT = "CaterogyFragment";
    final static String LIST_FEED_FRAGMENT = "ListFeedFragment_";
    final static String FEED_VIEW_FRAGMENT = "FeedViewFragment";
    @ViewById
    Toolbar toolbar;
    @ViewById
    AppBarLayout appBarLayout;
    Menu menu;
    Integer feedId;
    Feed feedOnView;
    String category;
    Runnable changeColor;
    Runnable changeTextSize;
    AlertDialog.Builder popDialog;

    @AfterViews
    public void init() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new CaterogyFragment_(), CATEGORY_FRAGMENT).commit();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Prepare textSizePopup
        ShowDialog();



    }
    public void ShowDialog()
    {
        popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        final TextView demoText = new TextView(this);
        demoText.setText("Demo");
        final LinearLayout layouut = new LinearLayout(this);
        layouut.setOrientation(LinearLayout.VERTICAL);
        layouut.addView(seek);
        layouut.addView(demoText);
        seek.setMax(100);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Please Select Rank 1-100 ");
        popDialog.setView(layouut);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                demoText.setTextSize(progress);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });


        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });


        popDialog.create();

    }



    @Override
    public void onCategorySelected(String category) {
    this.category = category;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

        ListFeedFragment sharedElementFragment1 = new ListFeedFragment_();

        fragmentTransaction.replace(R.id.fragment, sharedElementFragment1, LIST_FEED_FRAGMENT).addToBackStack(null).commit();

        getSupportActionBar().setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setVisibilityForAllItem(boolean visibility) {
        if(this.menu==null)return;
        for(int index=0;index<this.menu.size();index++){
            this.menu.getItem(index).setVisible(visibility);

        }

    }

    @Override
    public void onSelectFeed(Feed feed, View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("feedId", feed.getContentID());
        getSupportActionBar().setTitle(feed.getSourceName());
        setVisibilityForAllItem(true);
        startFeedViewFragment(bundle, v);
    }

    private void startFeedViewFragment(Bundle bundle, View v) {
        FeedViewActivity_ sharedElementFragment2 = new FeedViewActivity_();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
        sharedElementFragment2.setArguments(bundle);
        fragmentTransaction
                .replace(R.id.fragment, sharedElementFragment2, FEED_VIEW_FRAGMENT)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onBackFeedList(Feed feedBefore) {

        feedId = feedBefore.getContentID();
    }

    @Override
    public void feedOnView(Feed feed) {
        feedOnView = feed;

    }

    @Override
    public void setRunningFragment(FragmentEnum fragment) {
        switch(fragment){
            case CATEROGY:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setVisibilityForAllItem(false);

                getSupportActionBar().setTitle(null);


                break;
            case LIST_FEED:
                getSupportActionBar().setTitle(category);

                if(feedId!=null){
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    ListFeedFragment_ listFeedFragment_ = (ListFeedFragment_) fragmentManager.findFragmentByTag(LIST_FEED_FRAGMENT);

                    setVisibilityForAllItem(false);
                    listFeedFragment_.scrollToIndex(feedId);
                }
                break;
            case FEED:
                setVisibilityForAllItem(true);
                break;

            default:
                break;


        }
    }

    @Override
    public void changeColor(Runnable runnable) {
        this.changeColor= runnable;
    }

    @Override
    public void changeTextSize(Runnable runnable) {
        this.changeTextSize = runnable;

    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void share() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, feedOnView.getContentUrl());
        startActivity(intent);
    }

    private void openSource() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feedOnView.getContentUrl()));
        startActivity(i);
    }
    private void changeSize(){

            popDialog.show();






    }
    private void changeColor(){
        if(changeColor!=null){
            changeColor.run();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share:
                share();
                break;
            case R.id.web:
                openSource();
                break;
            case R.id.size:
                changeSize();
                break;
            case R.id.color:
                changeColor();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_view, menu);
        this.menu = menu;
        setVisibilityForAllItem(false);
        return true;
    }
}
