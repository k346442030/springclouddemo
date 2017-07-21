package com.platform.server.enums;

public enum ReviewStatus {
    
    NO_REVIEWED(0, "待审核"),
    PASS(1, "审核通过"),
    NO_PASS(2, "审核不通过");
    
    private final int val;
    private final String description;
    private String toString;
    
    private ReviewStatus(Integer val, String description) {
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
