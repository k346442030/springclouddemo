package com.platform.service.business.controller.request.inputModelBase;

import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.controller.request.BaseReqForm;

public class BaseInputJSONForm extends BaseReqForm {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private JSONObject form;
    
    public JSONObject getForm() {
        return form;
    }
    
    public void setForm(JSONObject form) {
        this.form = form;
    }
    
    public void setObject(Object obj) {
        this.form = JSONObject.parseObject(JSONObject.toJSONString(obj));
    }
    
}
