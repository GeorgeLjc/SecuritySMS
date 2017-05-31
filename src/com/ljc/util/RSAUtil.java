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
	//private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    private static final int DEFAULT_KEY_SIZE = 1024;//秘钥默认长度
    //private static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    //private static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数
	
    public static KeyPair generateRSAKeyPair()  
    {  
        return generateRSAKeyPair(DEFAULT_KEY_SIZE);  
    }
	
	/**生成RSA算法的公钥和私钥**/
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
	
	/**使用公钥加密**/
	public static String encryptByPublicKey(String Msg, String publicKey) throws Exception {
		//将String类型的公钥转化为PublicKey的公钥
		byte[] keyBytes=new BASE64Decoder().decodeBuffer(publicKey); 
	    X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
	    KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
	    Key RSA_PubKey =keyFactory.generatePublic(keySpec);
	    
	    //将将要加密的数据转化为Byte类型
	    byte [] byte_Msg=Msg.getBytes();
	    
		// 加密数据
        Cipher cp = Cipher.getInstance(keyFactory.getAlgorithm());
        cp.init(Cipher.ENCRYPT_MODE, RSA_PubKey);
        byte[] encodeMsg =cp.doFinal(byte_Msg);
        return (new String(new BASE64Encoder().encode(encodeMsg)));
    }
	
	/**使用公钥解密**/
	public static String decryptByPublicKey(String Msg, String publicKey) throws Exception {
			//将String类型的公钥转化为PublicKey的公钥
			byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(publicKey); 
			X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
			Key RSA_PubKey =keyFactory.generatePublic(keySpec);
		    
		
	        // 数据解密
	        Cipher cipher = Cipher.getInstance(RSA);
	        cipher.init(Cipher.DECRYPT_MODE, RSA_PubKey);
	        byte[] byte_Msg=new BASE64Decoder().decodeBuffer(Msg);
	        byte[] decodeMsg=cipher.doFinal(byte_Msg);
	        return (new String(decodeMsg));
	 }
	
	/**使用私钥加密**/
	public static String encryptByPrivateKey(String Msg, String privateKey) throws Exception {
		//将String类型的公钥转化为PublicKey的公钥
		byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(privateKey); 
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
		KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
		Key RSA_PriKey =keyFactory.generatePrivate(keySpec);

	    //将将要加密的数据转化为Byte类型
	    byte [] byte_Msg=Msg.getBytes();
	    
	   // 加密数据
       Cipher cp = Cipher.getInstance(RSA);
       cp.init(Cipher.ENCRYPT_MODE, RSA_PriKey);
       byte[] encodeMsg =cp.doFinal(byte_Msg);
       return (new String(new BASE64Encoder().encode(encodeMsg)));
   }
	
	/**使用私钥解密**/
	public static String decryptByPrivateKey(String Msg, String PrivateKey) throws Exception {
		//将String类型的公钥转化为PublicKey的公钥
		byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(PrivateKey); 
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
/*ok*/	KeyFactory keyFactory=KeyFactory.getInstance(RSA);  
/*bug*/	Key RSA_PriKey =keyFactory.generatePrivate(keySpec);

//        // 数据解密
		Cipher cp = Cipher.getInstance(keyFactory.getAlgorithm());
        cp.init(Cipher.DECRYPT_MODE, RSA_PriKey);
        byte[] byte_Msg=new BASE64Decoder().decodeBuffer(Msg);
        byte[] decodeMsg=cp.doFinal(byte_Msg);
        return (new String(decodeMsg));
		//return "1";
	}
}
