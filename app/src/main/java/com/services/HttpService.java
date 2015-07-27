package com.services;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.feed.Feed;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.concurrent.ExecutionException;

@EBean
public class HttpService{
    @RootContext
    Context context;

public String readUrl(String path,Context context,Boolean cached){

    try {
        return  Ion.with(context).load(path).asString().get();
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(context,"Get fail",Toast.LENGTH_SHORT).show();
        return "";
    }
    
}
   public void loadImage(Feed feed,ImageView imageView){
       Ion.getDefault(context).build(imageView).load(feed.getImage());

    }
}