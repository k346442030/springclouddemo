package com.platform.service.business.controller.response;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.entity.BaseEntity;

public class EntityListResponse extends BaseResponse {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public <T extends BaseEntity<?>> EntityListResponse(Page<T> page) {
		if(page.getContent() != null){
			this.dataList = JSONArray.parseArray(JSONObject.toJSONString(page.getContent()));
		}
		this.page = page.getNumber() + 1;
		this.total = page.getTotalElements();
	}

	public EntityListResponse() {

	}

	private JSONArray dataList;

	private Integer page;

	private Long total;

	public JSONArray getDataList() {
		return dataList;
	}

	public void setDataList(JSONArray dataList) {
		this.dataList = dataList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
