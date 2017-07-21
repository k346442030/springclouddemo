package com.platform.service.business.entity;

import java.io.Serializable;

public abstract class BaseEntity<K extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract K keyValue();
}
