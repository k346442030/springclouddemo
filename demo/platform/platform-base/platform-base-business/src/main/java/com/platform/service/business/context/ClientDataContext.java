package com.platform.service.business.context;

import com.alibaba.fastjson.JSONObject;

public class ClientDataContext {

	private static final String NAMESPACE = ClientDataContext.class.getName();

	private static final String KEY_ACCESS_TOKEN = NAMESPACE + ".access_token";

	private static final String KEY_CLIENT = NAMESPACE + ".client";

	public static JSONObject getClient() {
		return (JSONObject) get(KEY_CLIENT);
	}

	public static void setClient(JSONObject t) {
		set(KEY_CLIENT, t);
	}

	public static String getAccessToken() {
		return (String) get(KEY_ACCESS_TOKEN);
	}

	public static void setAccessToken(String access_token) {
		set(KEY_ACCESS_TOKEN, access_token);
	}

	public static Object get(String key) {
		return DataContext.get(key);
	}

	public static Object set(String key, Object value) {
		return DataContext.set(key, value);
	}

	public static Object remove(String key) {
		return DataContext.remove(key);
	}

	public static void clear() {
		DataContext.clear();
	}
}
