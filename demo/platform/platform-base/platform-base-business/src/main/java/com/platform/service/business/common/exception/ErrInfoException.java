package com.platform.service.business.common.exception;

import com.platform.service.business.common.errors.ErrorInfoBase;

public class ErrInfoException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected Integer errorNo;

	protected String errorInfo;

	protected String errorOn;

	protected Object errorValue;

	public ErrInfoException(Integer errorNo, String errorInfo) {
		this(errorInfo, errorNo, errorInfo);
	}

	public ErrInfoException(Throwable cause, Integer errorNo, String errorInfo) {
		this(errorInfo, cause, errorNo, errorInfo);
	}

	public ErrInfoException(String message, Integer errorNo, String errorInfo) {
		super(message);
		this.errorNo = errorNo;
		this.errorInfo = errorInfo;
	}

	public ErrInfoException(String message, Throwable cause, Integer errorNo, String errorInfo) {
		super(message, cause);
		this.errorNo = errorNo;
		this.errorInfo = errorInfo;
	}

	public ErrInfoException(Integer errorNo, String errorInfo, String errorOn, Object errorValue) {
		this(buildErrorMessageWithDetail(errorInfo, errorOn, errorValue), errorNo, errorInfo, errorOn, errorValue);
	}

	public ErrInfoException(String message, Integer errorNo, String errorInfo, String errorOn, Object errorValue) {
		super(message);
		this.errorNo = errorNo;
		this.errorInfo = errorInfo;
		this.errorOn = errorOn;
		this.errorValue = errorValue;
	}

	public ErrInfoException(String message, Throwable cause, Integer errorNo, String errorInfo, String errorOn,
			Object errorValue) {
		super(message, cause);
		this.errorNo = errorNo;
		this.errorInfo = errorInfo;
		this.errorOn = errorOn;
		this.errorValue = errorValue;
	}

	public ErrInfoException(Throwable cause, Integer errorNo, String errorInfo, String errorOn, Object errorValue) {
		this(buildErrorMessageWithDetail(errorInfo, errorOn, errorValue), cause, errorNo, errorInfo, errorOn,
				errorValue);
	}

	public ErrInfoException(ErrorInfoBase errInfo, String info) {
		this.errorNo = errInfo.getError_no();
		this.errorInfo = errInfo.getError_info() + ':' + info;
	}

	public ErrInfoException(ErrorInfoBase errInfo) {
		this.errorNo = errInfo.getError_no();
		this.errorInfo = errInfo.getError_info();
	}

	public ErrInfoException(Throwable cause, Integer errorNo) {
		super(cause);
		this.errorNo = errorNo;
	}

	public ErrInfoException(String message, Throwable cause, Integer errorNo, Object errorValue) {
		this(message, cause, errorNo, null, null, errorValue);
	}

	public ErrInfoException(String message, Integer errorNo, Object errorValue) {
		this(message, errorNo, null, null, errorValue);
	}

	public ErrInfoException(String message, Throwable cause, Integer errorNo, String errorOn, Object errorValue) {
		this(message, cause, errorNo, null, errorOn, errorValue);
	}

	public static String buildErrorMessageWithDetail(String baseMessage, String errorOn, Object errorValue) {
		return new StringBuilder().append(null == baseMessage ? "" : baseMessage).append('[').append(errorOn)
				.append('=').append(errorValue).append(']').toString();
	}

	public boolean isErrorOf(Integer errorNo) {
		return null != this.errorNo && this.errorNo.equals(errorNo);
	}

	public boolean isErrorIn(String... errorNos) {
		if (null != this.errorNo) {
			if (null != errorNos && 0 < errorNos.length) {
				for (String errorNo : errorNos) {
					if (this.errorNo.toString().equals(errorNo)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Integer getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(Integer errorNo) {
		this.errorNo = errorNo;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getErrorOn() {
		return errorOn;
	}

	public void setErrorOn(String errorOn) {
		this.errorOn = errorOn;
	}

	public Object getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(Object errorValue) {
		this.errorValue = errorValue;
	}

}
