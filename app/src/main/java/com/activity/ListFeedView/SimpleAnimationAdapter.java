package com.activity.ListFeedView;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;
import com.model.Feed;
import com.phuchieu.news.R;
import com.services.FeedService;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class SimpleAnimationAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    @RootContext
    Context context;
    @Bean
    HttpService httpService;
    @Bean
    FeedService feedService;
    private List<Feed> feeds = new ArrayList<Feed>();
    private int mDuration = 500;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = 10;
    private boolean isFirstOnly = true;
    private ViewHolder.OnClickListener onClickListener;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!(viewHolder instanceof ViewHolder)) return;
        ViewHolder holder = (ViewHolder) viewHolder;


        if (position < getItemCount() && (customHeaderView != null ? position <= feeds.size() : position < feeds.size()) && (customHeaderView != null ? position > 0 : true)) {
            Feed item = feeds.get(position);
            holder.feed = item;

            TextView title = holder.title;

            ImageView imageView = holder.imageView;

            title.setText(item.getTitle());
            title.setEllipsize(TextUtils.TruncateAt.END);
            title.setMaxLines(2);
            holder.onClickListener = this.getOnClickListener();

            httpService.loadImage(item.getLandscapeAvatar(), imageView, holder.progressBar);

            TextView sourceInfo = holder.sourceInfo;
            sourceInfo.setText(feedService.getSource(item.getContentUrl()));
            ImageView sourceImage = holder.sourceImage;
            sourceImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            feedService.getIconOfUrl(item.getContentUrl(), sourceImage);
        }
        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(holder.itemView, AdapterAnimationType.SlideInBottom)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }

    }


    @Override
    public int getAdapterItemCount() {
        return feeds.size();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_view_big_image, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setDataList(List<Feed> feeds) {
        clear(this.feeds);
        setMoreDataList(feeds);
    }

    public void setMoreDataList(List<Feed> feedList) {
        for (Feed feed : feedList) {
            insert(feeds, feed, feeds.size());
        }
    }


    public void clear() {
        clear(feeds);
        mLastPosition = 0;
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    @Override
    public long generateHeaderId(int position) {
        return Long.parseLong(feeds.get(position).getContentID() + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    public ViewHolder.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(ViewHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Feed getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < feeds.size())
            return feeds.get(position);
        else return null;
    }


}
