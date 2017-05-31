package com.ljc.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import android.util.Base64;

public class DESUtil {
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding"; 
	private static byte[] iv = "0123456789ABCDEF".getBytes();
	public static String encryptDES(String msg, String key) throws Exception {  
//		IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);  
//        IvParameterSpec zeroIv = new IvParameterSpec(iv);  
//        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");  
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
//        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
//        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());  
//        return new String (encryptedData);
        //return Base64.encodeToString(encryptedData,Base64.DEFAULT);  
		try{  
			DESKeySpec dks = new DESKeySpec(key.getBytes());  

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            //key的长度不能够小于8位字节  
            Key secretKey = keyFactory.generateSecret(dks);  
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);  
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());  
            AlgorithmParameterSpec paramSpec = iv;  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);  
              
            byte[] data=msg.getBytes();
            byte[] bytes = cipher.doFinal(data);  
              
            return new BASE64Encoder().encode(bytes); 
            
	        }catch(Throwable e){  
	                e.printStackTrace();  
	        }  
	        return " @DES.encrypt_error ";
    }  
	public static String decryptDES(String msg, String key) throws Exception {  
		 try  
	        {  
			 	byte[] data=new BASE64Decoder().decodeBuffer(msg);
	            SecureRandom sr = new SecureRandom();  
	            DESKeySpec dks = new DESKeySpec(key.getBytes());  
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	            //key的长度不能够小于8位字节  
	            Key secretKey = keyFactory.generateSecret(dks);  
	            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);  
	            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());  
	            AlgorithmParameterSpec paramSpec = iv;  
	            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);  
	            
	            byte[] bytes= cipher.doFinal(data); 
	            return new String(bytes);
	        } catch (Exception e)  
	        {  
	            throw new Exception(e);  
	        }    
    }  
}
