package com.arthpay.arthpay_java;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;

public class OrderClient {
	
	String auth;
	private static Gson gson = new Gson();
	String orderId;
	private enum Method {
	    GET, POST, PUT, PATCH, DELETE
	}
	OrderClient(String auth) {
		this.auth = auth;
	}
	
	  public String create(String request) throws ArthpayException {
		var response = postRequest(Constants.VERSION, Constants.ORDER_CREATE, request, auth);
		orderId = response.getMsg();
		System.out.println("order id : "+response.getMsg());
	    return response.getObj().toString();
	  }
	  
	  public String checkStatus() throws ArthpayException {
		  if(orderId==null)
			  return "Please create order prior to checking status";
		  
		  OrderStatusPojo status = new OrderStatusPojo();
		  status.setOrderId(orderId);
		  
		  var requestJson = gson.toJson(status);
		  return postRequest(Constants.VERSION, Constants.ORDER_STATUS, requestJson, auth).getObj().toString();
	  }

	  
	  private static ResponsePojo postRequest(String version, String path, String requestObject, String auth)
	          throws ArthpayException {
	    return postCreateRequest(version, path, requestObject, auth, Constants.API);
	  }
	 
	  private static ResponsePojo postCreateRequest(String version, String path, String requestObject, String auth, String host) throws ArthpayException {

		  try {
		  URIBuilder builder = ApiUtility.getBuilder(version, path, host);
			
			String requestBody;
		  
			if(requestObject != null){
			   requestBody = requestObject;
			}else{
				requestBody = requestObject;// == "" ? "" : requestObject;//.toString();//requestContent;//HttpRequest.BodyPublishers.ofString(requestContent);
			}
			System.out.println("Request body : "+requestBody);
			
			HttpRequest request =
					postOrder(Method.POST.name(), builder.build().toString(), requestBody, auth);
			@SuppressWarnings("rawtypes")
			HttpResponse response = ApiUtility.processRequest(request);
			var responseObj = gson.fromJson(response.body().toString(), ResponsePojo.class);
			
			return responseObj;
		  }
		  catch (Exception e) {
			  throw new ArthpayException(e.getMessage());
		  }
	  }
	  
	  private static HttpRequest postOrder(String method, String url, String requestBody,
		      String auth) throws ArthpayException {
		  System.out.println("createRequest | url is : "+url);
		  HttpRequest.Builder builder = HttpRequest.newBuilder()
				  				.uri(URI.create(url))
				  				.method(method, HttpRequest.BodyPublishers.ofString(requestBody));
		  
		  CredentialManager creds = new CredentialManager();
		  
		  System.out.println("clientId not null : "+auth);
		    if (auth!=null) {
		    	var auth_secret = auth.split(",/,");
		    	
		    	System.out.println("Using : "+auth_secret[1]+" to encrypt!");
		    	String secret =  creds.getJwsSignature(requestBody, auth_secret[1]);
		    	System.out.println("secret not null : "+secret);
		    	
		    	builder.setHeader(Constants.HEADER_CLIENT_ID, auth_secret[0]);
		    	builder.setHeader(Constants.HEADER_SECRET, secret);
		    }

		    builder.setHeader(Constants.USER_AGENT, "Arthpay/v1 JAVASDK/" + ApiUtility.version + " Java/" + System.getProperty("java.version"));
		    builder.setHeader(Constants.HTTP_HEADER_CONTENT_TYPE, Constants.HTTP_HEADER_APPLICATION_JSON);

		    for (Map.Entry<String, String> header : ApiUtility.headers.entrySet()) {
		      builder.setHeader(header.getKey(), header.getValue());
		    }

		    return builder.build();
	  }	  
}
