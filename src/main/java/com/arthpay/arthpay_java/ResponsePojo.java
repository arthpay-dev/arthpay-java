package com.arthpay.arthpay_java;

//import org.springframework.http.HttpStatusCode;

public class ResponsePojo<T> {

//	private HttpStatusCode statusCode;
	private int statusCode;
	
	private String msg;
	
	private T obj;
	
	public ResponsePojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ResponsePojo(int sttusCode, String msg) {
		super();
		this.statusCode = sttusCode;
		this.msg = msg;
	}
	
	public ResponsePojo(int sttusCode, String msg, T obj) {
		super();
		this.statusCode = sttusCode;
		this.msg = msg;
		this.obj = obj;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
}