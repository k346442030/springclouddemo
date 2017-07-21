package com.platform.server.controller;

import java.io.Serializable;

import com.platform.service.business.controller.base.BaseController;
import com.platform.service.business.entity.BaseEntity;

public abstract class BusiBaseController<T extends BaseEntity<K>, K extends Serializable>
        extends BaseController<T, K> {
    
}
