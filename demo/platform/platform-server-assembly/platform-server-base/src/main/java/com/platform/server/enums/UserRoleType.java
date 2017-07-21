package com.platform.server.enums;

public enum UserRoleType {
    
    SELLER(0, "卖家"),
    MANAGER(1, "管理员");
    
    private final int val;
    private final String description;
    private String toString;
    
    private UserRoleType(Integer val, String description) {
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
