package com.platform.service.business.controller.request;

import java.io.Serializable;

import com.platform.service.business.config.ConfigValues;

public class BaseReqForm implements Serializable {

	/**
	 * BaseReqForm serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void superToken() {
		this.accessToken = ConfigValues.SUPER_TOKEN;
	}

}
