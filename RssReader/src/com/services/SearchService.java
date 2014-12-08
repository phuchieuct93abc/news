package com.services;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.feed.FeedContent;

import android.util.Log;

public class SearchService {
	public static void search() {
		try {

			String google = "https://www.google.com/search?q=";
			String search = "stackoverflow";
			String charset = "UTF-8";	
			Elements links = Jsoup
					.connect(google + URLEncoder.encode(search, charset))
					.get().select(".srg .g");
			for (Element link : links) {

				String title = link.select("h3").text();
				String url = link.select("h3 > a").attr("href");
				String discription = link.select(".s .st").text();

			}
		} catch (Exception e) {
			Log.i("error", e.getMessage());
		}
	}
}
