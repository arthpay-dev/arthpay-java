package com.arthpay.arthpay_java;

import org.springframework.http.HttpStatusCode;

public class ResponsePojo<T> {

	private HttpStatusCode statusCode;
	private int sttusCode;
	
	private String msg;
	
	private T obj;
	
	public ResponsePojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ResponsePojo(HttpStatusCode statusCode, String Msg) {
		super();
		this.statusCode = statusCode;
		this.msg = Msg;
	}

	public ResponsePojo(HttpStatusCode statusCode, String Msg, T obj) {
		super();
		this.statusCode = statusCode;
		this.msg = Msg;
		this.obj = obj;
	}
	
	public ResponsePojo(int sttusCode, String Msg) {
		super();
		this.sttusCode = sttusCode;
		this.msg = Msg;
	}
	
	public ResponsePojo(int sttusCode, String Msg, T obj) {
		super();
		this.sttusCode = sttusCode;
		this.msg = Msg;
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


	public String getMsg() {
		return msg;
	}

	public void setMsg(String Msg) {
		this.msg = Msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
}