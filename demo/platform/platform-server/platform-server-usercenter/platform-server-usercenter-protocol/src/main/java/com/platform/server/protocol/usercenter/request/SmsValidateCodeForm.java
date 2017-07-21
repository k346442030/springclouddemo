package com.platform.server.protocol.usercenter.request;

import com.platform.service.business.controller.request.BaseReqForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "短信验证码入参")
public class SmsValidateCodeForm extends BaseReqForm {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "手机号", position = 1)
    private String mobile;
    
    private Integer verify_type;
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public Integer getVerify_type() {
        return verify_type;
    }
    
    public void setVerify_type(Integer verify_type) {
        this.verify_type = verify_type;
    }
    
}
