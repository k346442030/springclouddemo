package com.platform.service.business.common.enums;

public enum DeleteFlag {

	NOT_DELETE(0, "未删除"), DELETE(1, "已删除");

	private int value;

	private String desc;

	DeleteFlag(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
