package com.services;

import com.model.Video;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by ACER on 3/15/2017.
 */
@EBean
public class UtilService {
    public String getVideo(String html){
        Document doc = Jsoup.parse(html);
        Element element = doc.select("video source").first();
        if(element !=null){

            return element.attr("data-src");
        }
        else{
            return null;

        }


    }

}
