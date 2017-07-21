package com.platform.service.business.controller.response;

public class TokenResponse extends BaseResponse {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String accessToken;

	private Long expire;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

}
