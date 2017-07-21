package com.platform.service.business.controller.response;


import com.alibaba.fastjson.JSONObject;

public class EntityResponse extends BaseResponse {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private JSONObject data;

	public EntityResponse(Object t) {
		this.data = JSONObject.parseObject(JSONObject.toJSONString(t));
	}

	public EntityResponse() {

	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

}
