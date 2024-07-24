package com.arthpay.arthpay_java;

import java.util.Map;

public class ArthpayMerchant {
	
	public CreateOrder orders;
	private String auth;
	  
	public ArthpayMerchant(String key, String secret) throws ArthpayException {
	    this(key, secret, false);
	}
	public ArthpayMerchant(String key, String secret, Boolean enableLogging) throws ArthpayException {
		this.auth = CredentialManager.GetAuth(key, secret);
		initMerchant(enableLogging);
	}
	private void initMerchant(Boolean enableLogging) throws ArthpayException {
		ApiUtility.createHttpClientInstance(enableLogging);
		orders = new CreateOrder(this.auth);
	}
	
	public ArthpayMerchant addHeaders(Map<String, String> headers) {
		ApiUtility.addHeaders(headers);
		return this;
	}
}
