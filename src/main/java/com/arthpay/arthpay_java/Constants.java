package com.arthpay.arthpay_java;

import org.springframework.http.MediaType;

public class Constants {
	// API constants
	  static final String SCHEME = "http";
	  static final String API = "API";
	  static final String AUTH = "AUTH";
	  static final String HOSTNAME = "callback.arthpay.com";
	  static final String AUTH_HOSTNAME = "callback.arthpay.com";
	  static final Integer PORT = 443;
	  static final String VERSION = "v1";

	  static final String VERSION_V2 = "v2";

	  static final String AUTH_HEADER_KEY = "Authorization";
	  static final String HEADER_CLIENT_ID = "x-client-id";
	  static final String HEADER_SECRET = "x-client-secret";
	  static final String USER_AGENT = "User-Agent";
	  static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
	  
	  static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	  static final String HTTP_HEADER_APPLICATION_JSON = "application/json";

	  
	  static final String ORDER_CREATE = "/wrapper/merchant/orderCreate";
	  static final String ORDER_STATUS = "/wrapper/merchant/orderStatus";
	  static final String ORDER_EDIT = "/wrapper/merchant/orderCreate/%s";
	  static final String ORDER_LIST = "/wrapper/merchant/orderCreate";
	  static final String ORDER_PAYMENT_LIST = "/wrapper/merchant/orderCreate/%s/payments";
	  
	  static final String WRAP_ORD_STATUS_INITIATED = "INIT";
	  static final String WRAP_ORD_STATUS_PENDING = "PENDING";
	  static final String WRAP_ORD_STATUS_SUCCESS = "SUCCESS";
	  
}
