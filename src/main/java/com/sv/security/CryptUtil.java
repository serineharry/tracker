package com.sv.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtil {

	private final static String SECRET="ThisIsmy$ecretKe";
	
	
	public static String encrypt(String token){
    	String encStr = "";
    	
    	Cipher cipher;
		try {
			 Key aesKey = new SecretKeySpec(SECRET.getBytes(), "AES");
	            
			 cipher = Cipher.getInstance("AES");
	         // encrypt the text
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         byte[] encrypted = cipher.doFinal(token.getBytes());
	            
	         encStr = Base64.getEncoder().encodeToString(encrypted);
	        
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return encStr;
    }
	
	public static String decrypt(String token){
    	String decStr = "";
    	
    	Key aesKey = new SecretKeySpec(SECRET.getBytes(), "AES");
    	Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(token));
            decStr = new String(decrypt);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return decStr;
    }
	
	public static void main(String[] args) {
		    	
    	String enc = CryptUtil.encrypt("sree123");
    	System.out.println(enc);
    	System.out.println("enc done");
    	
    	String dec = CryptUtil.decrypt("iZWBZ9FXaW0l0GBk3As9GA==");
    	System.out.println(dec);
    	    	
    	
	}

}
