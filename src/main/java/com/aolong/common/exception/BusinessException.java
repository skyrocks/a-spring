package com.aolong.common.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -3876502758804606346L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable th) {
	    super(message, th);
	}

	public BusinessException(Throwable th) {
		super(th);
	}

	public String getMessage() {
		return super.getMessage();
	}
}