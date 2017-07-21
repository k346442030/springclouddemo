package com.platform.server.protocol.usercenter.enums;

public enum VerifyCodeType {
    
    REGIST(1, "注册"),
    RESET_PASSWORD(2, "充值密码"),
    PAY(3, "支付");

	private final int val;
	private final String description;
	private String toString;

	private VerifyCodeType(Integer val, String description) {
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
