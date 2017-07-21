package com.platform.server.protocol.usercenter.request;

import org.hibernate.validator.constraints.NotBlank;

import com.platform.service.business.controller.request.BaseReqForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ManagerLoginForm extends BaseReqForm{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@ApiModelProperty("账号")
	private String account;
	
	@NotBlank
	@ApiModelProperty("密码")
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
