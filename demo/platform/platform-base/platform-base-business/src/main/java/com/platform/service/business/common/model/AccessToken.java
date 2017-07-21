package com.platform.service.business.common.model;

import java.io.Serializable;

public class AccessToken implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	private Long expire;

	public AccessToken() {

	}

	public AccessToken(String token, Long expire) {
		this.token = token;
		this.expire = expire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

}
