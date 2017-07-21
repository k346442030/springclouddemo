package com.platform.service.business.controller.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BaseResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "错误号")
	private Integer error_no;

	@ApiModelProperty(value = "错误信息")
	private String error_info;

	public Integer getError_no() {
		return error_no;
	}

	public void setError_no(Integer error_no) {
		this.error_no = error_no;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

}
