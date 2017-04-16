package com.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.component.CustomVideoPlayer;
import com.phuchieu.news.R;


public class DemoVideo extends AppCompatActivity {

    CustomVideoPlayer customVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        customVideoPlayer = (CustomVideoPlayer) findViewById(R.id.customVideoPlayer);
        String videoUrl = "http://baomoi-video.r.za.zdn.vn/af2ae754ef4e7926fda0dc65d772b326/58ca57d4/video.viettimes.vn/2017_03_15/lemai/cnngoihanoilacainoicuadisan1489479819_1.mp4";

        customVideoPlayer.setUrl(videoUrl);
        customVideoPlayer.ready();

    }

}
