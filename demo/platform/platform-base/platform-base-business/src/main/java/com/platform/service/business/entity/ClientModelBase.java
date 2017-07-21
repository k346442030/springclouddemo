package com.platform.service.business.entity;

import java.io.Serializable;

public abstract class ClientModelBase implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    public abstract String getClientIdStr();
    
}
