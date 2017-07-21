package com.platform.service.business.controller.request.inputModelBase;

import com.platform.service.business.controller.request.BaseReqForm;

public class BaseKeyForm<K> extends BaseReqForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BaseKeyForm() {

	}

	public BaseKeyForm(K key) {
		this.key = key;
	}

	private K key;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

}
