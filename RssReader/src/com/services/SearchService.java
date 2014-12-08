package com.services;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.feed.Feed;

public class SearchService {
	public static Elements search(String key) {
		try {
			String charset = "UTF-8";	
			String google = "http://www.baomoi.com/Search.aspx?t=3&ph="+ URLEncoder.encode(key, charset)+"&zoneid=-1";
			Elements links = Jsoup
					.connect(google)
					.get().select(".story");
			return links;
		} catch (Exception e) {
			//Log.i("error", e.getMessage());
			return null;

		}
	}
}
