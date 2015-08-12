package com.services;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.feed.Feed;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class HttpService{
    @RootContext
    Context context;
    LoadBuilder<Builders.Any.B> ionLoadUrl;
    Ion ionLoadImage;

public String readUrl(String path,Context context){

    try {
        initIonLoadUrl(context);
        return  ionLoadUrl.load(path).asString().get();
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(context,"Get fail",Toast.LENGTH_SHORT).show();
        return "";
    }
    
}

    private void initIonLoadUrl(Context context) {
        if(ionLoadUrl ==null){
            ionLoadUrl =  Ion.with(context);}
    }

    public void loadImage(Feed feed,ImageView imageView){
        initIonLoadImage();
        Ion.getDefault(context).build(imageView).load(feed.getImage());

    }

    private void initIonLoadImage() {
        if(ionLoadImage==null) ionLoadImage = Ion.getDefault(context);
    }

}