package com.integrate.exception;

import com.integrate.enums.IMsgEnumType;

public class BusinessException extends RuntimeException{

	
	private int errorCode;
	
	private String errorMessage;

	public BusinessException() {
		super();
	}

	public BusinessException(IMsgEnumType msgEnumType) {
		super(msgEnumType.getMessage());
		this.errorCode = msgEnumType.getCode();
		this.errorMessage = msgEnumType.getMessage();
	}
	
	public BusinessException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
	
	

}
