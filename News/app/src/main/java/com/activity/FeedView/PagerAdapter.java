package com.activity.FeedView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.activity.ListFeedView.FeedViewFragment;
import com.activity.ListFeedView.FeedViewFragment_;

import java.util.List;

class PagerAdapter extends FragmentStatePagerAdapter {

    String link;
    List<String> listLink;
    Context context;
    int textSize;

    public void setTextSize(int textSize) {
        Log.i("hieu","text size"+textSize);
        this.textSize = textSize;
    }


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<String> getListLink() {
        return listLink;
    }

    public void setListLink(List<String> categoryLink) {
        this.listLink = categoryLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FeedViewFragment_();
        ((FeedViewFragment) fragment).setContext(this.context);
        ((FeedViewFragment) fragment).setLink(getListLink().get(i));
        Log.i("hieu", this.textSize + "");
        ((FeedViewFragment) fragment).setTextSizePref(this.textSize);

        return fragment;
    }

    @Override
    public int getCount() {
        return listLink.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
    /*	String feedLink = getListLink().get(position);
		// String title = FeedService.getFeedContent(feedLink).getTitle();
*/
        return "NEWS " + position;
    }
}