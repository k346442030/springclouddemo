package com.platform.service.business.cache.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ClientSession implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private JSONObject client;

	private String clientId;

	public JSONObject getClient() {
		return client;
	}

	public void setClient(JSONObject client) {
		this.client = client;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String toStringInfo() {
		return JSON.toJSONString(this);
	}
}
