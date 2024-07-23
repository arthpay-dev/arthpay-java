package com.arthpay.arthhpay_java;

//import java.io.IOArthpayException;
import java.net.URI;
//import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiClient {


	  String auth;

	  private final String ENTITY = "entity";

//	  private final String COLLECTION = "collection";

	  private final String ERROR = "error";

	  private final String DESCRIPTION = "description";

	  private final String STATUS_CODE = "code";

	  private final int STATUS_OK = 200;

	  private final int STATUS_MULTIPLE_CHOICE = 300;

	  ApiClient() { }

	  ApiClient(String auth) {
	    this.auth = auth;
	  }

	  public <T extends Entity> T get(String version, String path, JSONObject requestObject) throws ArthpayException {
	    return get(version, path, requestObject, Constants.API);
	  }

	  public <T extends Entity> T get(String version, String path, JSONObject requestObject, String host) throws ArthpayException {
	    HttpResponse response = ApiUtility.getRequest(version, path, requestObject, auth, host);
	    return processResponse(response);
	  }

	  public <T> T post(String version, String path, JSONObject requestObject) throws ArthpayException {
	    return post(version, path, requestObject, Constants.API);
	  }

	  public <T> T post(String version, String path, JSONObject requestObject, String host) throws ArthpayException {
		HttpResponse response = ApiUtility.postRequest(version, path, requestObject, auth, host);
	    return processResponse(response);
	  }

//	  public <T extends Entity> T put(String version, String path, JSONObject requestObject) throws ArthpayException {
//	    return put(version, path, requestObject, Constants.API);
//	  }
//
//	  public <T extends Entity> T put(String version, String path, JSONObject requestObject, String host) throws ArthpayException {
//		HttpResponse response = ApiUtility.putRequest(version, path, requestObject, auth, host);
//	    return processResponse(response);
//	  }
//
//	  public <T extends Entity> T patch(String version, String path, JSONObject requestObject) throws ArthpayException {
//	    return patch(version, path, requestObject, Constants.API);
//	  }
//
//	  public <T extends Entity> T patch(String version, String path, JSONObject requestObject, String host) throws ArthpayException {
//		HttpResponse response = ApiUtility.patchRequest(version, path, requestObject, auth, host);
//	    return processResponse(response);
//	  }
//
//
//	  <T extends Entity> ArrayList<T> getCollection(String version, String path, JSONObject requestObject)
//	          throws ArthpayException {
//	    HttpResponse response = ApiUtility.getRequest(version, path, requestObject, auth);
//	    return processCollectionResponse(response);
//	  }
//
//	  public <T> T delete(String version, String path, JSONObject requestObject) throws ArthpayException {
//		  HttpResponse response = ApiUtility.deleteRequest(version, path, requestObject, auth);
//	    return processDeleteResponse(response);
//	  }

//	  private <T extends Object> T processDeleteResponse(HttpResponse response) throws ArthpayException {
//	    if (response == null) {
//	      throw new ArthpayException("Invalid Response from server");
//	    }
//
//	    int statusCode = response.statusCode();
//	    String responseBody = null;
//	    JSONObject responseJson = null;
//
//	    try {
//	      responseBody = response.body().toString();
//	      if(responseBody.equals("[]")){
//	        return (T) Collections.emptyList();
//	      }
//	      else if(response.statusCode()==204){
//	        return null;
//	      }
//	      else{
//	        responseJson = new JSONObject(responseBody);
//	      }
//	    } catch (ArthpayException e) {
//	      throw new ArthpayException(e.getMessage());
//	    }
//
//	    if (statusCode < STATUS_OK || statusCode >= STATUS_MULTIPLE_CHOICE) {
//	      throwArthpayException(statusCode, responseJson);
//	    }
//	    return (T) parseResponse(responseJson, getEntity(responseJson, response.request().uri()));
//	  }

	  private <T extends Entity> T parseResponse(JSONObject jsonObject, String entity) throws ArthpayException {
	    if (entity != null) {
	      Class<T> cls = getClass(entity);
	      try {
	        return cls.getConstructor(JSONObject.class).newInstance(jsonObject);
	      } catch (Exception e) {
	        throw new ArthpayException("Unable to parse response because of " + e.getMessage());
	      }
	    }

	    throw new ArthpayException("Unable to parse response");
	  }

	  private <T extends Entity> ArrayList<T> parseCollectionResponse(JSONArray jsonArray, URI requestUrl)
	      throws ArthpayException {

	   ArrayList<T> modelList = new ArrayList<T>();
	    try {
	      for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject jsonObj = jsonArray.getJSONObject(i);
	        T t = parseResponse(jsonObj, getEntity(jsonObj,requestUrl));
	        modelList.add(t);
	      }
	      return modelList;
	    } catch (ArthpayException e) {
	      throw e;
	    }
	  }

	  /*
	   * this method will take http url as : https://api.razorpay.com/v1/invocies
	   * and will return entity name with the help of @EntityNameURLMapping class
	   */
	  private String getEntityNameFromURL(URI url) {
	    String param = url.getPath();
	    return EntityNameURLMapping.getEntityName(param);
	  }


	  <T extends Object> T processResponse(HttpResponse response) throws ArthpayException {
	    if (response == null) {
	      throw new ArthpayException("Invalid Response from server");
	    }

	    int statusCode = response.statusCode();
	    String responseBody = null;
	    JSONObject responseJson = null;
	    try {
	      responseBody = response.body().toString();
	      if(responseBody.equals("[]")){
	        return (T) Collections.emptyList();
	      } else{
	        responseJson = new JSONObject(responseBody);
	      }
	    } catch (Exception e) {
	      throw new ArthpayException(e.getMessage());
	    }

	    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
	      return (T) parseResponse(responseJson, getEntity(responseJson, response.request().uri()));
	    }

	    throwArthpayException(statusCode, responseJson);
	    return null;
	  }

	  <T extends Entity> ArrayList<T> processCollectionResponse(HttpResponse response)
	          throws ArthpayException {
	    if (response == null) {
	      throw new ArthpayException("Invalid Response from server");
	    }

	    int statusCode = response.statusCode();
	    String responseBody = null;
	    JSONObject responseJson = null;

	    try {
	      responseBody = response.body().toString();
	      responseJson = new JSONObject(responseBody);
	    } catch (Exception e) {
	      throw new ArthpayException(e.getMessage());
	    }

	    String collectionName  = null;
	    collectionName = responseJson.has("payment_links")?
	            "payment_links": "items";

	    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
	      return parseCollectionResponse(responseJson.getJSONArray(collectionName), response.request().uri());
	    }

	    throwArthpayException(statusCode, responseJson);
	    return null;
	  }

	  private String getEntity(JSONObject jsonObj, URI url) {
	    if(!jsonObj.has(ENTITY)) {
	      return getEntityNameFromURL(url);
	    }else if(getClass(jsonObj.get("entity").toString()) == null){
	      return getEntityNameFromURL(url);
	    }else{
	      return jsonObj.getString(ENTITY);
	    }
	  }

	  private void throwArthpayException(int statusCode, JSONObject responseJson) throws ArthpayException {
	    if (responseJson.has(ERROR)) {
	      JSONObject errorResponse = responseJson.getJSONObject(ERROR);
	      String code = errorResponse.getString(STATUS_CODE);
	      String description = errorResponse.getString(DESCRIPTION);
	      throw new ArthpayException(code + ":" + description);
	    }
	    throwServerArthpayException(statusCode, responseJson.toString());
	  }

	  private void throwServerArthpayException(int statusCode, String responseBody) throws ArthpayException {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Status Code: ").append(statusCode).append("\n");
	    sb.append("Server response: ").append(responseBody);
	    throw new ArthpayException(sb.toString());
	  }

	  private Class getClass(String entity) {
	    try {
	      String entityClass = "com.razorpay." + WordUtils.capitalize(entity, '_').replaceAll("_", "");
	      return Class.forName(entityClass);
	    } catch (ClassNotFoundException e) {
	      return null;
	    }
	  }
}
