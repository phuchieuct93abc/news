package com.activity.ListFeedView;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.model.Feed;
import com.phuchieu.news.R;

public class ViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
    TextView title;
    ImageView imageView;
    Feed feed;
    OnClickListener onClickListener;
    ProgressBar progressBar;
    MaterialRippleLayout materialRippleLayout;
    TextView sourceInfo;
    ImageView sourceImage;
    CardView cardView;


    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        imageView = (ImageView) itemView.findViewById((R.id.imageView));
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        materialRippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        sourceInfo = (TextView) itemView.findViewById(R.id.sourceInfo);
        sourceImage = (ImageView) itemView.findViewById(R.id.sourceImage);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        materialRippleLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        onClickListener.onClick(feed, imageView);
    }


    public interface OnClickListener {
        void onClick(Feed feed, View view);
    }
}