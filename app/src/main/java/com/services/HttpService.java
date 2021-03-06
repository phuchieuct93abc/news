package com.services;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


@EBean
public class HttpService {
    private static String DEFAULT_URL = "http://etaal.gov.in/etaal/Image/news.png";
    @RootContext
    Context context;

    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Picasso picasso;

    @AfterInject
    public void initIon() {
        ionLoadUrl = Ion.with(context.getApplicationContext());
        picasso = Picasso.with(context.getApplicationContext());
    }


    public String readUrl(final String path) throws Exception {
        final String clonedPath = path;
        Integer runningTime = 0;

        while (runningTime <= 10) {
            runningTime++;
            try {

                String result = ionLoadUrl.load(clonedPath).asString().get();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                if (runningTime < 5) {
                    continue;
                } else {
                    throw new Exception();

                }
            }
        }
        return null;


    }


    public void loadImage(String url, final ImageView imageView, final ProgressBar progressBar) {
        try {

            if (url.isEmpty() || url.length() == 0) {

                loadDefaultImage(imageView);
                if(progressBar!=null){

                    progressBar.setVisibility(View.GONE);
                }
                return;


            }

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            picasso.load(url).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onError() {
                    loadDefaultImage(imageView);

                    if (progressBar != null) {

                        progressBar.setVisibility(View.GONE);

                    }
                }
            });

        } catch (Exception e) {
        }


    }

    private void loadDefaultImage(ImageView imageView) {
        picasso.load(DEFAULT_URL).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }


}