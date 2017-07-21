package com.platform.server.protocol.usercenter.request;

import com.platform.service.business.common.annotation.Mobile;
import com.platform.service.business.controller.request.BaseReqForm;
import org.hibernate.validator.constraints.NotBlank;

public class ResetPwdForm extends BaseReqForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Mobile
	@NotBlank
	private String mobile;

	@NotBlank
	private String validate_code;

	@NotBlank
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidate_code() {
		return validate_code;
	}

	public void setValidate_code(String validate_code) {
		this.validate_code = validate_code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
