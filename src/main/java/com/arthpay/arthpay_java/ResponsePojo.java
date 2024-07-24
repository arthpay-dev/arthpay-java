package com.arthpay.arthpay_java;

import org.springframework.http.HttpStatusCode;

public class ResponsePojo<T> {

	private HttpStatusCode statusCode;
	private int sttusCode;
	
	private String errMsg;
	
	private T obj;
	
	public ResponsePojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ResponsePojo(HttpStatusCode statusCode, String errMsg) {
		super();
		this.statusCode = statusCode;
		this.errMsg = errMsg;
	}

	public ResponsePojo(HttpStatusCode statusCode, String errMsg, T obj) {
		super();
		this.statusCode = statusCode;
		this.errMsg = errMsg;
		this.obj = obj;
	}
	
	public ResponsePojo(int sttusCode, String errMsg) {
		super();
		this.sttusCode = sttusCode;
		this.errMsg = errMsg;
	}
	
	public ResponsePojo(int sttusCode, String errMsg, T obj) {
		super();
		this.sttusCode = sttusCode;
		this.errMsg = errMsg;
		this.obj = obj;
	}

	public HttpStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public int getSttusCode() {
		return sttusCode;
	}

	public void setSttusCode(int sttusCode) {
		this.sttusCode = sttusCode;
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