package com.platform.server.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
@SuppressWarnings("deprecation")
public class HttpUtil {


	public static String doPost(String url) {
		HttpClient httpClient = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost get = new HttpPost(url);
			HttpResponse response = httpClient.execute(get);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "UTF-8");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
