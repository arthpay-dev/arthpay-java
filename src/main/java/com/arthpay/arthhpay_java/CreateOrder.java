package com.arthpay.arthhpay_java;

import org.json.JSONObject;

public class CreateOrder extends ApiClient {
	CreateOrder(String auth) {
		super(auth);
	}
	
	public String create(JSONObject request) throws Exception{
		return post(Constants.VERSION, Constants.ORDER_CREATE, request);
	}
}
