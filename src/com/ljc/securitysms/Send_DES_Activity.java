package com.ljc.securitysms;

import java.util.ArrayList;

import com.example.securitysms.R;
import com.ljc.util.DESUtil;
import com.ljc.util.FileUtil;
import com.ljc.util.RSAUtil;

import android.R.string;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Send_DES_Activity extends Activity implements OnClickListener {

	EditText number,content,des_key;
	Button send_key,send_dmsg;
	SmsManager sManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.des_send);
		sManager = SmsManager.getDefault();
		//获取文本框和按钮
		number = (EditText)findViewById(R.id.pnumber);
		content = (EditText)findViewById(R.id.message);
		des_key = (EditText)findViewById(R.id.des_key);
		send_key = (Button)findViewById(R.id.send_deskey);
		send_dmsg = (Button)findViewById(R.id.DES_send);
		
		send_key.setOnClickListener(this);
		send_dmsg.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String pnumber=number.getText().toString();
		String key=des_key.getText().toString();
		String msg=content.getText().toString();
		switch(v.getId()){
		case R.id.send_deskey:
			String publicKey="";
			publicKey = FileUtil.readFileSdcard(FileUtil.File_NAME+pnumber+".txt");
			
			String ciphermsg="@des_key:";
			try {
				ciphermsg+=RSAUtil.encryptByPublicKey(key, publicKey);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			PendingIntent PI = PendingIntent.getActivity(Send_DES_Activity.this, 0, new Intent(), 0);
			if (ciphermsg.length() > 70) {  
			    ArrayList<String> msgs = sManager.divideMessage(ciphermsg);  
			    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
			    for(int i = 0;i<msgs.size();i++){  
			       sentIntents.add(PI);  
			    }  
			    //sManager.sendTextMessage(number.getText().toString(), null, ciphermessage, pi, null);
			    sManager.sendMultipartTextMessage(number.getText().toString(), null, msgs, sentIntents, null);  
			    Toast.makeText(Send_DES_Activity.this, "发送DES密钥成功>70", 8000).show();
			} else {   
				sManager.sendTextMessage(number.getText().toString(), null, ciphermsg, PI, null);  
				Toast.makeText(Send_DES_Activity.this, "发送DES密钥成功<=70", 8000).show();
			} 
			
			break;
		case R.id.DES_send:
			
			String cipherMsg="@msg_des:";
			
			try {
				/*无法正确加密，加密返回结果为空, 原因 ： 需要八位密钥 测试的时候使用6位 API:http://www.apihome.cn/api/android/DESKeySpec.html*/
				//cipherMsg+=DESUtil.encryptDES(msg, key); 
				String cmsg=DESUtil.encryptDES(msg, key); 
				//String dmsg=DESUtil.decryptDES(cmsg, key);
				cipherMsg+=cmsg;
				//cipherMsg+=msg+" "+key;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PendingIntent pi = PendingIntent.getActivity(Send_DES_Activity.this, 0, new Intent(), 0);
			if (cipherMsg.length() > 70) {  
			    ArrayList<String> msgs = sManager.divideMessage(cipherMsg);  
			    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
			    for(int i = 0;i<msgs.size();i++){  
			       sentIntents.add(pi);  
			    }  
			    //sManager.sendTextMessage(number.getText().toString(), null, ciphermessage, pi, null);
			    sManager.sendMultipartTextMessage(number.getText().toString(), null, msgs, sentIntents, null);  
			    Toast.makeText(Send_DES_Activity.this, "发送DES密文成功>70", 8000).show();
			} else {   
				sManager.sendTextMessage(number.getText().toString(), null, cipherMsg, pi, null);  
				Toast.makeText(Send_DES_Activity.this, "发送DES密文成功<=70", 8000).show();
			} 
			break;
		default:
				break;
		}
	}

	
}
