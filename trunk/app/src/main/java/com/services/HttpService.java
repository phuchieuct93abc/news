package com.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.phuchieu.news.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;

@EBean
public class HttpService {
    @RootContext
    Context context;
    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Ion ionLoadImage;
    int timeout = 2000;


    public String readUrl(String path) {
        SharedPreferences sharedPref = context.getSharedPreferences("HttpPreference", Context.MODE_PRIVATE);
        String result;
        try {
            initIonLoadUrl(context);
            result = ionLoadUrl.load(path).setTimeout(timeout).asString().get();
            sharedPref.edit().putString(path, result).commit();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result = sharedPref.getString(path, null);
            if (result != null) {
                return result;
            } else {
                Toast.makeText(context, "Get fail", Toast.LENGTH_SHORT).show();
                return "";
            }

        }

    }

    private void initIonLoadUrl(Context context) {
        if (ionLoadUrl == null) {
            ionLoadUrl = Ion.with(context);
        }
    }

    public void loadImage(String url, ImageView imageView) {
        initIonLoadImage();
        Ion.getDefault(context)
                .build(imageView)
                .animateIn(R.animator.fade_in)
                .animateLoad(R.animator.fade_out)
                .placeholder(R.drawable.news)
                .load(url);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }


    private void initIonLoadImage() {
        if (ionLoadImage == null) ionLoadImage = Ion.getDefault(context);
    }

    public void setRandomImage(final ImageView imageView) {
        try {

            String newString = "http://lorempixel.com/300/450/?time=" + new Date().toString();

            Log.d("hieu123", newString);
            loadImage(newString, imageView);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}