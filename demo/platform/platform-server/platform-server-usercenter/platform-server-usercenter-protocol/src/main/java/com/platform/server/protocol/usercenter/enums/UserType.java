package com.platform.server.protocol.usercenter.enums;

public enum UserType {
    
    MANAGER(1, "管理员");

	private final int val;
	private final String description;
	private String toString;

	private UserType(Integer val, String description) {
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
