package com.platform.server.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
@SuppressWarnings("deprecation")
public class HttpsUtil {

	@SuppressWarnings({ "unchecked" })
	public static String doPost(String url, Map<String, Object> map) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<?> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> elem = (Entry<String, Object>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
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

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doGet(String url, Map<String, Object> params) {
		return doGet(url, params, false);
	}

	public static String doGet(String url, Map<String, Object> params, boolean proxyFlag) {
		HttpClient httpClient = null;
		String result = null;
		try {
			if (params != null && !params.isEmpty()) {
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(convertPairs(params), "UTF-8"));
			}
			httpClient = getHttpClient(proxyFlag);

			HttpGet get = new HttpGet(url);
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

	@SuppressWarnings("resource")
	public static JSONObject post(String url, String postData, String charset) {
		JSONObject json = new JSONObject();
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			StringEntity se = new StringEntity(new String(postData.getBytes("UTF-8"), "UTF-8"), "utf-8");
			se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
			return JSONObject.parseObject(result);
		} catch (Exception e) {
		}
		return json;
	}

	public static List<NameValuePair> convertPairs(Map<String, Object> params) {
		if (null != params && !params.isEmpty()) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String value = String.valueOf(entry.getValue());
				if (value != null) {
					pairs.add(new BasicNameValuePair(entry.getKey(), value));
				}
			}
			return pairs;
		}
		return null;
	}

	// 设置代理

	public static HttpClient getHttpClient(boolean proxyFlag) throws Exception {
		SSLClient httpClient = new SSLClient();
		if (!proxyFlag) {
			return httpClient;
		}
		String proxyHost = "183.196.170.247";
		int proxyPort = 9000;
		String userName = "";
		String password = "";
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort),
				new UsernamePasswordCredentials(userName, password));
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		return httpClient;
	}
}
