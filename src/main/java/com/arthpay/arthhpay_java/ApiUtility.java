package com.arthpay.arthhpay_java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class ApiUtility {
	
	private static HttpClient client;
	private static Map<String, String> headers = new HashMap<String, String>();
	
	private static String version = null;
	
	static void createHttpClientInstance(boolean enableLog) throws ArthpayException {
		if(client==null) {
			final Logger LOGGER = LogManager.getLogger();
			
			if(enableLog) {
				LOGGER.isEnabled(Level.DEBUG);
			} else {
				LOGGER.isEnabled(Level.OFF);
			}
		 try {
	        client = HttpClient.newBuilder()
	        		.connectTimeout(Duration.ofSeconds(60))
					.build();
	      } catch (Exception e) {
	        throw new ArthpayException(e);
	      }
		}
		Properties properties = new Properties();
		try {
			properties.load(ApiUtility.class.getResourceAsStream("/arthpayproject.peoperties"));
			version = (String) properties.get("version");
		} catch (Exception e) {
		      throw new ArthpayException(e.getMessage());
	    }
	}
	
	private enum Method {
	    GET, POST, PUT, PATCH, DELETE
	  }
	
	 static HttpResponse postRequest(String version, String path, JSONObject requestObject, String auth)
	          throws ArthpayException {
	    return postRequest(version, path, requestObject, auth, Constants.API);
	  }
	 
	  static HttpResponse postRequest(String version, String path, JSONObject requestObject, String auth, String host) throws ArthpayException {

		  try {
		  URIBuilder builder = getBuilder(version, path, host);
			
//			BodyPublisher requestBody;
			String requestBody;
		  
			if(requestObject != null && requestObject.has("file")){
			   requestBody = null; //fileRequestBody(requestObject);
			}else{
			  String requestContent = requestObject == null ? "" : requestObject.toString();
			  requestBody = requestContent;//HttpRequest.BodyPublishers.ofString(requestContent);
			}
			
			HttpRequest request =
			    createRequest(Method.POST.name(), builder.build().toString(), requestBody, auth);
			return processRequest(request);
		  }
		  catch (Exception e) {
			  throw new ArthpayException(e.getMessage());
		  }
	  }
	  
	  static HttpResponse getRequest(String version, String path, JSONObject requestObject, String auth) throws ArthpayException {
	    return getRequest(version, path, requestObject, auth, Constants.API);
	  }

	  static HttpResponse getRequest(String version, String path, JSONObject requestObject, String auth, String host) throws ArthpayException {
		  try {
			URIBuilder builder = getBuilder(version, path, host);
		    addQueryParams(builder, requestObject);
		    HttpRequest request = createRequest(Method.GET.name(), builder.build().toString(), null, auth);
		    return processRequest(request);
		  }
		  catch (Exception e) {
			  throw new ArthpayException(e.getMessage());
		  }
	  }
	  
	  private static void addQueryParams(URIBuilder builder, JSONObject request) {
		    if (request == null)
		      return;
		    
		    
		    Iterator<?> keys = request.keys();
		    while (keys.hasNext()) {
		      String key = (String) keys.next();
		      builder.addParameter(key, request.get(key).toString());
		    }
		  }
	  static void addHeaders(Map<String, String> header) {
		    headers.putAll(header);
		  }
	  
	  private static HttpRequest createRequest(String method, String url, String requestBody,
		      String auth) throws ArthpayException {
		  HttpRequest.Builder builder = HttpRequest.newBuilder()
				  				.uri(URI.create(url))
				  				.method(method, HttpRequest.BodyPublishers.ofString(requestBody));
		  
		  CredentialManager creds = new CredentialManager();
		  
		    if (auth != null && auth.equals(creds.getClientId())) {
		    	String secret =  creds.getJwsSignature(requestBody);
		    	builder.setHeader(Constants.HEADER_CLIENT_ID, auth);
		    	builder.setHeader(Constants.HEADER_SECRET, secret);
		    }

		    builder.setHeader(Constants.USER_AGENT, "Arthpay/v1 JAVASDK/" + version + " Java/" + System.getProperty("java.version"));
		    builder.setHeader(Constants.HTTP_HEADER_CONTENT_TYPE, Constants.HTTP_HEADER_APPLICATION_JSON);

		    for (Map.Entry<String, String> header : headers.entrySet()) {
		      builder.setHeader(header.getKey(), header.getValue());
		    }

		    return builder.build();
	  }
	  
	  
	  private static URIBuilder getBuilder(String version, String path, String host) throws ArthpayException {
		  URIBuilder builder;
		    switch (host)
		    {
		      case Constants.API:
		        builder = getAPIBuilder(version, path);
		        break;
		      case Constants.AUTH:
		        builder = getOAuthBuilder(path);
		        break;
		      default:
		        builder = getAPIBuilder(version, path);
		    }
		    return builder;
		  }
	  
	  private static URIBuilder getAPIBuilder(String version, String path) throws ArthpayException {
		    return new URIBuilder()
		    		.setScheme(Constants.SCHEME)
		    		.setHost(Constants.HOSTNAME)
		    		.setPort(Constants.PORT);
		    		//(Constants.SCHEME,null, Constants.HOSTNAME,Constants.PORT, path, null,null);
	  }

	  private static URIBuilder getOAuthBuilder(String path) throws ArthpayException {
		  return new URIBuilder()
		    		.setScheme(Constants.SCHEME)
		    		.setHost(Constants.HOSTNAME)
		    		.setPort(Constants.PORT);
	  }
	  
	  private static String getMediaType(String fileName){
		    int extensionIndex = fileName.lastIndexOf('.');
		    String extenionName = fileName.substring(extensionIndex + 1);
		    if(extenionName == "jpg" | extenionName == "jpeg" | extenionName == "png" | extenionName == "jfif"){
		      return "image/jpg";
		    }
		      return "image/pdf";
	  }
	  private static HttpResponse processRequest(HttpRequest request) throws ArthpayException {
		    try {
		      return client.send(request, HttpResponse.BodyHandlers.ofString());
		    } catch (Exception e) {
		      throw new ArthpayException(e.getMessage());
		    }
		  }
	  
//	  private static MultiValueMap<String,HttpEntity<?>> fileRequestBody(JSONObject requestObject){
//		    File fileToUpload = new File((String) requestObject.get("file"));
//		    String fileName = fileToUpload.getName();
//
//		    MediaType mediaType = MediaType.parseMediaType(getMediaType(fileName));
//		    MultiValueMap<String,HttpEntity<?>> fileBody = RequestBody.create(mediaType, fileToUpload);
//
//		    MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
//		    
//		    multipartBodyBuilder.part(fileName, multipartBodyBuilder, mediaType) ;//("file",fileName, fileBody);
//
//		    Iterator<?> iterator = requestObject.keys();
//		    while (iterator.hasNext()) {
//		      Object key = iterator.next();
//		      Object value = requestObject.get(key.toString());
//		      multipartBodyBuilder.part((String) key, (String) value);
//		    }
//
//		    var requestBody = multipartBodyBuilder.build();
//		    return requestBody;
//	  }
}
