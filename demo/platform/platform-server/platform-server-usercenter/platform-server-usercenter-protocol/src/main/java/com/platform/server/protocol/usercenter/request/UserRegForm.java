package com.platform.server.protocol.usercenter.request;

import com.platform.server.protocol.usercenter.enums.VerifyCodeType;
import com.platform.service.business.common.annotation.EnumValue;
import com.platform.service.business.controller.request.inputModelBase.BaseInputJSONForm;

public class UserRegForm extends BaseInputJSONForm {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    @EnumValue(enumClass = VerifyCodeType.class)
    public String valiate_code;
    
    public String getValiate_code() {
        return valiate_code;
    }
    
    public void setValiate_code(String valiate_code) {
        this.valiate_code = valiate_code;
    }
    
}
