package com.arthpay.arthpay_java;


import com.google.gson.Gson;

public class CreateOrder extends OrderClient {
	
	private Gson gson = new Gson();
	
	CreateOrder(String auth) {
		super(auth);
	}
	
	public String create(CreateOrderRequest request) throws Exception{		
		return create(gson.toJson(request));
	}
	
	public String orderStatus() throws Exception {
		return checkStatus();
	}
}
