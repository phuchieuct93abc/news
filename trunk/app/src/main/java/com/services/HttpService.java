package com.services;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.feed.Feed;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.phuchieu.news.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

@EBean
public class HttpService {
    @RootContext
    Context context;
    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Ion ionLoadImage;
    private static String RANDOM_IMAGE = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=random&n=1&mkt=en-US";

    public String readUrl(String path) {

        try {
            initIonLoadUrl(context);
            return ionLoadUrl.load(path).asString().get();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Get fail", Toast.LENGTH_SHORT).show();
            return "";
        }

    }

    private void initIonLoadUrl(Context context) {
        if (ionLoadUrl == null) {
            ionLoadUrl = Ion.with(context);
        }
    }

    public void loadImage(Feed feed, ImageView imageView) {
        initIonLoadImage();
        Ion.getDefault(context).build(imageView).load(feed.getImage());

    }

    private void initIonLoadImage() {
        if (ionLoadImage == null) ionLoadImage = Ion.getDefault(context);
    }

    public void setRandomImage(ImageView imageView) {
        try {
            Random r = new Random();
            int i1 = r.nextInt(18 - 1) + 1;

            String response = readUrl(RANDOM_IMAGE.replaceAll("random", i1 + ""));
            JSONObject jObject = new JSONObject(response);
            JSONObject a = jObject.getJSONArray("images").getJSONObject(0);
            String url = "http://www.bing.com" + a.getString("url");
            Ion.getDefault(context).build(imageView).fitXY().load(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}