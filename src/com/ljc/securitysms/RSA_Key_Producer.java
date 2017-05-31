package com.ljc.securitysms;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import sun.misc.BASE64Encoder;

import com.example.securitysms.R;
import com.ljc.util.FileUtil;
import com.ljc.util.RSAUtil;
import com.ljc.util.SQLiteCon;



import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RSA_Key_Producer extends Activity implements OnClickListener {
	EditText receiver,privateK,pwd;
	TextView publicK;
	Button produce,send_pkey;
	String PublicKeyBytes,PrivateKeyBytes;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rsa_key_producer);
		receiver = (EditText)findViewById(R.id.publickey_receiver);
		publicK = (EditText)findViewById(R.id.public_key);
		pwd = (EditText)findViewById(R.id.pwd);
		produce = (Button)findViewById(R.id.create_rsa_key);
		send_pkey = (Button)findViewById(R.id.send_public_key);
		produce.setOnClickListener(this);
		send_pkey.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {  
        case R.id.create_rsa_key: 
         	SQLiteCon sql = new SQLiteCon(RSA_Key_Producer.this);
        	
         	String profile="localhost";
         	/*For Test*/
         	//sql.delete(profile);
        	String test_user=sql.queryUserPwd(profile);
        	//Toast.makeText(RSA_Key_Producer.this, test_user, 8000).show();
        	
        	if("".equals(test_user)){
        	//if(true){
        		KeyPair RsaKey_Pair=RSAUtil.generateRSAKeyPair();
            	PublicKey publicKey=(PublicKey)RsaKey_Pair.getPublic(); 
            	PrivateKey privateKey=(PrivateKey)RsaKey_Pair.getPrivate();
            	PublicKeyBytes = new String(new BASE64Encoder().encode(publicKey.getEncoded()));
            	PrivateKeyBytes = new String(new BASE64Encoder().encode(privateKey.getEncoded()));

//            	String bmsg="123abc";
//            	String cmsg="",dmsg="",emsg="";
//            	try {
//					cmsg=RSAUtil.encryptByPublicKey(bmsg, PublicKeyBytes);
//					dmsg=RSAUtil.decryptByPrivateKey(cmsg, PrivateKeyBytes);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	
            	publicK.setText(PublicKeyBytes);
//           
	        	FileUtil.folderCreate();        	
				FileUtil.writeFileSdcard(FileUtil.Folder_NAME+"/profile.txt", profile);
	//			System.out.println(sqlLH.query(userName));
				//将得到的自己的公钥存储到本地文件，以手机号命名
				FileUtil.writeFileSdcard(FileUtil.File_NAME+profile+".txt",PublicKeyBytes);
				
				
				String password= pwd.getText().toString();
	        	sql.insert(profile, PrivateKeyBytes,password);
				sql.update(profile, PrivateKeyBytes,password);
				Toast.makeText(RSA_Key_Producer.this, PrivateKeyBytes, 8000).show();
        	}else{
        		Toast.makeText(RSA_Key_Producer.this, "本地密钥对已经创建", 8000).show();
        	}
        	break;
        	
        case R.id.send_public_key:
        	
        	profile="localhost";
        	
			//将得到的自己的公钥存储到本地文件，以手机号命名
			String public_Key="@Public_key:";
			public_Key+=FileUtil.readFileSdcard(FileUtil.File_NAME+profile+".txt");
			
			//Toast.makeText(RSA_Key_Producer.this, public_Key, 8000).show();
			if(public_Key.length()<=12){
        		Toast.makeText(RSA_Key_Producer.this, "本地密钥对还未创建", 8000).show();
        	}else{
	        	String key_receiver = receiver.getText().toString();
	        	
	        	//Toast.makeText(RSA_Key_Producer.this, key_receiver+" "+public_Key, 8000).show();
	        	SmsManager sManager=SmsManager.getDefault();;
	        	PendingIntent pi = PendingIntent.getActivity(RSA_Key_Producer.this, 0, new Intent(), 0);
				//短信过长，采用分块发送
				if(public_Key.length()>70){
					ArrayList<String> msgs = sManager.divideMessage(public_Key);  
				    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
				    for(int i = 0;i<msgs.size();i++) 
				       sentIntents.add(pi);  
				    sManager.sendMultipartTextMessage(key_receiver, null, msgs, sentIntents, null);  
				}
				Toast.makeText(RSA_Key_Producer.this, "短信发送成功", 8000).show();
        	}
        	break;
        default:
        	break;
        	
		}
	}
}
