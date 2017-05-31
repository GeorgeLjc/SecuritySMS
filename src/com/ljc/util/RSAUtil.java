package com.ljc.util;
import java.math.BigInteger;  
import java.security.Key;
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.RSAPublicKeySpec;  
import java.security.spec.X509EncodedKeySpec; 

import javax.crypto.Cipher;

import android.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class RSAUtil {
	private static String RSA = "RSA";
	//private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//������䷽ʽ
    private static final int DEFAULT_KEY_SIZE = 1024;//��ԿĬ�ϳ���
    //private static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // ��Ҫ���ܵ����ݳ���bufferSize�������partSplit���зֿ����
    //private static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// ��ǰ��Կ֧�ּ��ܵ�����ֽ���
	
    public static KeyPair generateRSAKeyPair()  
    {  
        return generateRSAKeyPair(DEFAULT_KEY_SIZE);  
    }
	
	/**����RSA�㷨�Ĺ�Կ��˽Կ**/
	public static KeyPair generateRSAKeyPair(int keyLength)  
    {  
        try  
        {  
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);  
            kpg.initialize(keyLength);  
            return kpg.genKeyPair();  
        } catch (NoSuchAlgorithmException e)  
        {  
            e.printStackTrace();  
            return null;  
        }  
    } 
	
	/**ʹ�ù�Կ����**/
	public static String encryptByPublicKey(String Msg, String publicKey) throws Exception {
		//��String���͵Ĺ�Կת��ΪPublicKey�Ĺ�Կ
		byte[] keyBytes=new BASE64Decoder().decodeBuffer(publicKey); 
	    X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
	    KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
	    Key RSA_PubKey =keyFactory.generatePublic(keySpec);
	    
	    //����Ҫ���ܵ�����ת��ΪByte����
	    byte [] byte_Msg=Msg.getBytes();
	    
		// ��������
        Cipher cp = Cipher.getInstance(keyFactory.getAlgorithm());
        cp.init(Cipher.ENCRYPT_MODE, RSA_PubKey);
        byte[] encodeMsg =cp.doFinal(byte_Msg);
        return (new String(new BASE64Encoder().encode(encodeMsg)));
    }
	
	/**ʹ�ù�Կ����**/
	public static String decryptByPublicKey(String Msg, String publicKey) throws Exception {
			//��String���͵Ĺ�Կת��ΪPublicKey�Ĺ�Կ
			byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(publicKey); 
			X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
			Key RSA_PubKey =keyFactory.generatePublic(keySpec);
		    
		
	        // ���ݽ���
	        Cipher cipher = Cipher.getInstance(RSA);
	        cipher.init(Cipher.DECRYPT_MODE, RSA_PubKey);
	        byte[] byte_Msg=new BASE64Decoder().decodeBuffer(Msg);
	        byte[] decodeMsg=cipher.doFinal(byte_Msg);
	        return (new String(decodeMsg));
	 }
	
	/**ʹ��˽Կ����**/
	public static String encryptByPrivateKey(String Msg, String privateKey) throws Exception {
		//��String���͵Ĺ�Կת��ΪPublicKey�Ĺ�Կ
		byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(privateKey); 
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
		KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
		Key RSA_PriKey =keyFactory.generatePrivate(keySpec);

	    //����Ҫ���ܵ�����ת��ΪByte����
	    byte [] byte_Msg=Msg.getBytes();
	    
	   // ��������
       Cipher cp = Cipher.getInstance(RSA);
       cp.init(Cipher.ENCRYPT_MODE, RSA_PriKey);
       byte[] encodeMsg =cp.doFinal(byte_Msg);
       return (new String(new BASE64Encoder().encode(encodeMsg)));
   }
	
	/**ʹ��˽Կ����**/
	public static String decryptByPrivateKey(String Msg, String PrivateKey) throws Exception {
		//��String���͵Ĺ�Կת��ΪPublicKey�Ĺ�Կ
		byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(PrivateKey); 
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
/*ok*/	KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
/*bug*/	Key RSA_PriKey =keyFactory.generatePrivate(keySpec);

//        // ���ݽ���
		Cipher cp = Cipher.getInstance(keyFactory.getAlgorithm());
        cp.init(Cipher.DECRYPT_MODE, RSA_PriKey);
        byte[] byte_Msg=new BASE64Decoder().decodeBuffer(Msg);
        byte[] decodeMsg=cp.doFinal(byte_Msg);
        return (new String(decodeMsg));
		//return "1";
	}
}
