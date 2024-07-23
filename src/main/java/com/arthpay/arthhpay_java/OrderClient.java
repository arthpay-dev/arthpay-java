package com.arthpay.arthhpay_java;

import org.json.JSONObject;

public class OrderClient extends ApiClient {
	
	  OrderClient(String auth) {
	    super(auth);
	  }

	  public Order create(JSONObject request) throws ArthpayException {
	    return post(Constants.VERSION, Constants.ORDER_CREATE, request);
	  }
}
