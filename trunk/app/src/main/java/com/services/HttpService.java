package com.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.phuchieu.news.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

@EBean
public class HttpService {
    private static String RANDOM_IMAGE = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=random&n=1&mkt=en-US";
    public static String SPINNER = "https://d13yacurqjgara.cloudfront.net/users/159302/screenshots/1900376/material-spinner2.gif";
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
        if(!url.isEmpty()){
            Picasso.with(context).load(url).placeholder(R.drawable.news).into(imageView);

        }


//        Ion.getDefault(context).build(imageView).animateIn(R.animator.fade_in).animateLoad(R.animator.fade_out).error(R.drawable.news).load(url);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

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