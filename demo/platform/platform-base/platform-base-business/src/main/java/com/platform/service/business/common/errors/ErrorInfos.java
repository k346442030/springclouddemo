package com.platform.service.business.common.errors;

public class ErrorInfos {
    
    public enum CommonErr implements ErrorInfoBase {
        
        SUCCESS(0, "执行成功"),
        ERROR(1, "执行错误"),
        PARAM_ERR(1001, "参数错误"),
        SERVER_NOT_EXIST(1002, "后台服务未启动"),
        DATA_NOT_EXIST(1003, "记录不存在"),
        PARAM_MISS(1004, "参数缺失"),
        FILE_UPLOAD_ERR(1005, "文件上传失败"),
        FILE_MAX(1006, "文件太大"),
        FILE_FORMAT_ERR(1007, "文件格式错误"),
        FILE_NOT_EXIST(1008, "文件不存在"),
        SMS_SEND_ERR(1009, "短信发送失败"),
        DATA_EXIST(1010, "记录已存在"),
        BANK_CARD_ERR(1011, "银行卡号错误"),
        REPEAT_EXECUTE(1012, "重复操作"),
        
        ;
        
        private int error_no;
        
        private String error_info;
        
        CommonErr(int error_no, String error_info) {
            this.error_no = error_no;
            this.error_info = error_info;
        }
        
        public int getError_no() {
            return error_no;
        }
        
        public String getError_info() {
            return error_info;
        }
    }
    
    public enum ClientErr implements ErrorInfoBase {
        
        NO_LOGIN(2000, "未登录或已超时"),
        LOGIN_ERR(2001, "用户名或密码错误"),
        SELLER_EXIST(2002, "卖家信息已存在"),
        VALIDATE_CODE_ERR(2003, "验证码错误"),
        CLIENT_NOT_EXIST(2004, "用户不存在"),
        UPDATE_SELF(2005, "不能修改自己"),
        NO_PERMISSION(2006, "没有权限"),
        CURRENCY_NOT_EXIST(2007, "货币不存在"),
        PWD_ERR(2008, "密码错误"),
        NO_REALNAME_AUTH(2009, "尚未实名认证"),
        NO_REVIEWD_PASS(2010, "尚未审核通过"),
        COL_ACCOUNT_NOT_EXSIT(2011, "收款账号不存在"),
        MOBILE_EXIST(2012, "手机号已存在"),;
        
        private int error_no;
        
        private String error_info;
        
        ClientErr(int error_no, String error_info) {
            this.error_no = error_no;
            this.error_info = error_info;
        }
        
        public int getError_no() {
            return error_no;
        }
        
        public String getError_info() {
            return error_info;
        }
    }
    
    public enum BusinessErr implements ErrorInfoBase {
        
        CARD_CHECK_ERR(4000, "银行卡校验失败！"),
        BALANCE_NOT_ENOUGH(4001, "余额不足！"),
        ORDER_NOT_EXSIT(4002, "账单不存在"),
        ;
        
        private int error_no;
        
        private String error_info;
        
        BusinessErr(int error_no, String error_info) {
            this.error_no = error_no;
            this.error_info = error_info;
        }
        
        public int getError_no() {
            return error_no;
        }
        
        public String getError_info() {
            return error_info;
        }
    }
    
    public enum WeixinErr implements ErrorInfoBase {
        
        AUTH_ERR(3000, "微信授权失败"),;
        
        private int error_no;
        
        private String error_info;
        
        WeixinErr(int error_no, String error_info) {
            this.error_no = error_no;
            this.error_info = error_info;
        }
        
        public int getError_no() {
            return error_no;
        }
        
        public String getError_info() {
            return error_info;
        }
    }
    
}
