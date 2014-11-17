package com.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

@EBean
public class FeedService implements FeedServiceInterface {

	@Override
	public List<RssItem> getFeed(String source) {
		try {
			RssReader rssReader = new RssReader(source);

			List<RssItem> rssItems = rssReader.getItems();
			return rssItems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getResponseFromUrl(String url) {
		String xml = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return xml;
	}

}
