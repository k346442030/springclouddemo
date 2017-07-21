package com.platform.server.enums;

public enum SellerType {
    
    PERSINAL(1, "个人"),
    ENTERPRISE(2, "企业");

	private final int val;
	private final String description;
	private String toString;

	private SellerType(Integer val, String description) {
		this.val = val;
		this.description = description;
	}

	public Integer getVal() {
		return val;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		if (null == toString) {
			toString = new StringBuilder().append("RollingType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
}
