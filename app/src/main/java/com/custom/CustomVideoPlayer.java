package com.custom;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.model.Video;
import com.phuchieu.news.R;

public class CustomVideoPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener {
    String url;
    VideoView videoView;
    View rootView;
    ProgressBar progressBar;

    public CustomVideoPlayer(Context context) {
        super(context);
        init(context);
    }

    public  CustomVideoPlayer(Context context, AttributeSet attrs){

        super(context,attrs);
        init(context);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void ready(){
        if(this.url!=null){
            final Uri uri =Uri.parse(this.url);
            rootView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(uri);
            videoView.setZOrderOnTop(true);
            videoView.setOnPreparedListener(this);



        }
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.customvideo, this);
        videoView = (VideoView) rootView.findViewById(R.id.videoView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(rootView);
        rootView.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        videoView.setBackgroundColor(Color.TRANSPARENT);
        videoView.setZOrderOnTop(false);
        progressBar.setVisibility(View.GONE);
    }
}
