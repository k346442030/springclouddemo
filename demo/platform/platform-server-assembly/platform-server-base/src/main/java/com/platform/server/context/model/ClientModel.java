package com.platform.server.context.model;

import com.platform.service.business.entity.ClientModelBase;

public class ClientModel extends ClientModelBase {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String userName;

	private Integer userType;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Override
	public String getClientIdStr() {
		return userType + "&" + userId;
	}

}
