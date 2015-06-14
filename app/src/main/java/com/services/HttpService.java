package com.services;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;

@EBean
public class HttpService {
    private final static int timeout = 2000;

    public  String readUrl(String urlString, Context context) {
        try {
            Log.e("hieu", urlString);
            return Ion.with(context).load(urlString).noCache().setTimeout(timeout).asJsonObject().get().toString();


        } catch (Exception e) {
            try {
                return Ion.with(context).load(urlString).noCache().setTimeout(timeout).asJsonObject().get().toString();
            } catch (Exception e1) {
                Log.e("hieu", "that bat lan 2 " + urlString);
                return null;
            }
        }
    }
}
