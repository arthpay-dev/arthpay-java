package com.arthpay.arthpay_java;

public class ArthpayException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArthpayException(String message) {
	    super(message);
	  }

	  public ArthpayException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public ArthpayException(Throwable cause) {
	    super(cause);
	  }

	  public ArthpayException(String message, Throwable cause, boolean enableSuppression,
	      boolean writableStackTrace) {
	    super(message, cause, enableSuppression, writableStackTrace);
	  }
	}