package com.arthpay.arthpay_java;

import org.springframework.http.HttpStatusCode;

public class ResponsePojo<T> {

//	private HttpStatusCode statusCode;
	private int statusCode;
	
	private String errMsg;
	
	private T obj;
	
	public ResponsePojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ResponsePojo(int sttusCode, String errMsg) {
		super();
		this.statusCode = sttusCode;
		this.errMsg = errMsg;
	}
	
	public ResponsePojo(int sttusCode, String errMsg, T obj) {
		super();
		this.statusCode = sttusCode;
		this.errMsg = errMsg;
		this.obj = obj;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
}