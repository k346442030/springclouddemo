package com.platform.server.enums;

public enum SourceType {
    
    IQIYI("iqiyi", "爱奇艺"),
    YOUKU("youku", "优酷");

	private final String val;
	private final String description;
	private String toString;

	private SourceType(String val, String description) {
		this.val = val;
		this.description = description;
	}

	public String getVal() {
		return val;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		if (null == toString) {
			toString = new StringBuilder().append("SourceType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
}
