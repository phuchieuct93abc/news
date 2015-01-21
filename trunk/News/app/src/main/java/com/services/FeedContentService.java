package com.services;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class FeedContentService {
    public static Document getFeedContent(String link) {

        Element doc;
        String dataJson = null;
        try {

            doc = Jsoup.connect(link).timeout(5000).get().select("body").get(0);
            dataJson = doc.html();

        } catch (IOException e) {
            e.printStackTrace();
            dataJson="<p>Can not get content + "+link+"</p>";
        }finally {
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(dataJson); //convert the input stream to a json element
            JsonObject rootObject = root.getAsJsonObject(); //may be an array, may be an object.
            String bodyContentFromJson = rootObject.get("article").getAsJsonObject().get("Body").getAsString();
            return new Document(bodyContentFromJson);
        }
    }
}
