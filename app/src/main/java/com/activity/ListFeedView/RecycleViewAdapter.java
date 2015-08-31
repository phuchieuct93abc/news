package com.activity.ListFeedView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feed.Feed;
import com.phuchieu.news.R;
import com.services.HttpService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.PersonViewHolder> {

    List<Feed> feeds = new ArrayList<Feed>();


    RecycleViewAdapter(List<Feed> feeds) {
        this.feeds = feeds;
    }
    @RootContext
    Context context;
    @Bean
    HttpService httpService;
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_view_big_image, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Feed item = feeds.get(position);
        TextView title = holder.title;
        TextView description = holder.description;
        ImageView imageView = holder.imageView;

        if (!item.isRead(context)) {
            holder.title.setTextColor(Color.BLACK);
        } else {
            title.setTextColor(Color.WHITE);
        }
        title.setText(item.getTitle());
        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(2);
        description.setText(item.getContent());


        int width = title.getWidth();
        int height = title.getHeight();
        try{
            if(height/width*1020 >0) {
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(1020, height / width * 1020);
                imageView.setLayoutParams(parms);}

        }catch(Exception e){
            Log.d("hieu", height + " " + width);

        }
        httpService.loadImage(item, imageView);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }
    public void setDataList(List<Feed> feeds) {
        this.feeds = new ArrayList<>();
        this.feeds.addAll(feeds);
        this.notifyDataSetChanged();
    }

    public void setMoreDataList(List<Feed> feeds) {
        this.feeds.addAll( feeds);
        this.notifyDataSetChanged();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        PersonViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            imageView = (ImageView)itemView.findViewById((R.id.imageView));

        }
    }

}