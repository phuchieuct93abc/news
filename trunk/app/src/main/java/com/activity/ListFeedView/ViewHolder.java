package com.activity.ListFeedView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.model.Feed;
import com.phuchieu.news.R;

public class ViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
    TextView title;
    ImageView imageView;
    Feed feed;
    OnClickListener onClickListener;
    ProgressBar progressBar;


    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        imageView = (ImageView) itemView.findViewById((R.id.imageView));
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        onClickListener.onClick(feed, view);
    }


    public interface OnClickListener {
        void onClick(Feed feed, View view);
    }
}