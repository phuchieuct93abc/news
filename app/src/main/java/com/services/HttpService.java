package com.services;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;

import static android.view.View.GONE;

@EBean(scope = EBean.Scope.Singleton)
public class HttpService {
    private final static int TIMEOUT = 3000;
    private static String DEFAULT_URL = "http://etaal.gov.in/etaal/Image/news.png";
    @RootContext
    Context context;
    @Bean
    CacheProvider cacheProvider;
    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Picasso picasso;

    @AfterInject
    public void initIon() {
        ionLoadUrl = Ion.with(context.getApplicationContext());
        picasso = Picasso.with(context.getApplicationContext());
    }


    public String readUrl(String path) throws Exception {
        Integer runningTime = 0;

        while (runningTime <= 10) {
            runningTime++;
            Log.i("hieu run", "run");
            try {
                String cacheString = cacheProvider.get(path);
                if (cacheString == null) {
                    String result = null;
                    result = ionLoadUrl.load(path).setTimeout(TIMEOUT).asString().get();
                    cacheProvider.put(path, result);
                    return result;
                } else {
                    return cacheString;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (runningTime < 10) {
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

            }

            picasso.load(url).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    if (progressBar != null) {
                        progressBar.setVisibility(GONE);

                    }
                }

                @Override
                public void onError() {
                    loadDefaultImage(imageView);

                    if (progressBar != null) {

                        progressBar.setVisibility(GONE);

                    }
                }
            });

        } catch (Exception e) {
        }


    }

    private void loadDefaultImage(ImageView imageView) {
        picasso.load(DEFAULT_URL).into(imageView);
    }


    public void setRandomImage(final ImageView imageView) {
        try {

            String newString = "http://lorempixel.com/400/300/?time=" + new Date().toString();

            loadImage(newString, imageView, null);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}