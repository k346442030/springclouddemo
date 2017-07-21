package com.platform.server.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public abstract class ShuoshuUtil {

	public static String linkConvert(String playLink, String source) {
		try {
			String res = HttpsUtil.doGet("http://vpji.5233game.com/parse_ios.php?url=" + playLink);
			JSONObject json = JSONObject.parseObject(res);
			if (!StringUtils.isBlank(json.getString("error"))) {
				return null;
			}
			if ("le.com".equals(source)) {
				String url = json.getString("C");
				res = HttpsUtil.doGet(url);
				json = JSONObject.parseObject(res);
				return json.getString("location");
			} else if ("qq.com".equals(source)) {
				String vstr = json.getString("V");
				JSONArray array = JSONArray.parseArray(vstr);
				JSONObject jsonObject = array.getJSONObject(0);
				String url = jsonObject.getString("C");
				res = HttpsUtil.doGet(url);
				String s = res.split("\"fvkey\":\"")[1];
				String fvkey = s.substring(0, s.indexOf("\""));
				String realLink = jsonObject.getString("U");
				realLink = realLink.replace("vvvkk123", fvkey);
				return realLink;
			} else {
				String vstr = json.getString("V");
				JSONArray array = JSONArray.parseArray(vstr);
				JSONObject jsonObject = array.getJSONObject(0);
				String url = jsonObject.getString("U");
				return url;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
