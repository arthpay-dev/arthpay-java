package com.arthpay.arthpay_java;

import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CredentialManager {
	private String clientId;
	private String signature;

	public CredentialManager() {
		
	}
	public CredentialManager(String clientid, String sec){
		this.clientId = clientid;
//		this.secret = sec;
	}

	public static String GetAuth(String clientId, String secret) {
		new CredentialManager(clientId, secret);
		return clientId+",/,"+secret;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public String getJwsSignature(String data, String secretHash) throws ArthpayException {
		try {
//			var twoWay = false;
//			if(twoWay) {
//				byte [] decodedIV = Base64.getDecoder().decode(secretHash);
//				IvParameterSpec iv = new IvParameterSpec(decodedIV);
//		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//		        cipher.init(Cipher.ENCRYPT_MODE, getKey(), iv);
//		        byte[] cipherText = cipher.doFinal(data.getBytes());
//		        return Base64.getEncoder().encodeToString(cipherText);
//			
//			} else {
				SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
				PBEKeySpec spec = new PBEKeySpec(data.toCharArray(), secretHash.getBytes(),1000,256);
				byte[] hash = secretKey.generateSecret(spec).getEncoded();
				signature = Base64.getEncoder().encodeToString(hash);
				return signature;				
//			}
		} catch (Exception ex) {
			throw new ArthpayException(ex.getMessage());
		}
	}
	
	//get secret in base64 and convert to SecretKey format
//	private static SecretKey getKey() throws IOException {
//		UrlResource pemFile = new UrlResource(Constants.AES_KEY_PATH);
//		byte[] file = pemFile.getContentAsByteArray();
//		String key = new String(file, Charset.defaultCharset());
//		
//		byte[] decodedKey = Base64.getDecoder().decode(key);
//		SecretKey ogKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//		
//		return ogKey;
//	}
}
