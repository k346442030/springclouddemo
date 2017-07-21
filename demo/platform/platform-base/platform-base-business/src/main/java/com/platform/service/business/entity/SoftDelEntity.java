package com.platform.service.business.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class SoftDelEntity<K extends Serializable> extends BaseEntity<K> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "delete_flag")
	protected Integer deleteFlag;

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
