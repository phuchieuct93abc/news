package com.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.phuchieu.news.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;

import static android.view.View.GONE;

@EBean(scope = EBean.Scope.Singleton)
public class HttpService {
    @RootContext
    Context context;
    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Picasso picasso;
    private final static int TIMEOUT=2000;

    @AfterInject
    public void initIon() {
        ionLoadUrl = Ion.with(context.getApplicationContext());
        picasso = Picasso.with(context.getApplicationContext());

    }


    public String readUrl(String path) {
        SharedPreferences sharedPref = context.getSharedPreferences("HttpPreference", Context.MODE_PRIVATE);
        String result = "";
        try {
//            sharedPref.edit().putString(path, result).commit();
            return ionLoadUrl.load(path).setTimeout(TIMEOUT).asString().get();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Get fail", Toast.LENGTH_SHORT).show();

           // result = sharedPref.getString(path, null);
            if (result != null) {
                return result;
            } else {
                return "";
            }

        }

    }



    public void loadImage(String url, final ImageView imageView, final ProgressBar progressBar) {
        try{
            if(url.isEmpty() || url.length() == 0){
                if(progressBar!=null){
                    progressBar.setVisibility(GONE);

                }
                //imageView.setImageResource(R.drawable.news);

            }else{

                picasso.load(url)
                    .error(R.drawable.news)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if(progressBar!=null){
                                progressBar.setVisibility(GONE);

                            }
                        }

                        @Override
                        public void onError() {
                           // imageView.setImageResource(R.drawable.news);

                            if(progressBar!=null){

                                progressBar.setVisibility(GONE);

                            }
                        }
                    });
            }
        }catch(Exception e){
            Log.e("error", "loadImage: ", e);
        }


    }




    public void setRandomImage(final ImageView imageView) {
        try {

            String newString = "http://lorempixel.com/400/300/?time=" + new Date().toString();

            loadImage(newString, imageView,null);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}