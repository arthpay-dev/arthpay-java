package com.arthpay.arthhpay_java;

import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
public class CredentialManager {
	private String clientId;
	private String signature;
	private String secret = null;

	public CredentialManager() {
		
	}
	public CredentialManager(String clientId, String secret){
		this.clientId = clientId;
		this.secret = secret;
	}

	public static String GetAuth(String clientId, String secret) {
		new CredentialManager(clientId, secret);
		return clientId;
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public String getJwsSignature(String data) throws ArthpayException {
		try {
			SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			PBEKeySpec spec = new PBEKeySpec(data.toCharArray(), this.secret.getBytes(),1000,256);
			byte[] hash = secretKey.generateSecret(spec).getEncoded();
			
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception ex) {
			throw new ArthpayException(ex.getMessage());
		}
	}
}
