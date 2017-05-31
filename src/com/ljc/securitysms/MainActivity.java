package com.ljc.securitysms;

import com.example.securitysms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	Button enter_system,produce_key,decrypt_msg,send_desmsg;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		enter_system=(Button)findViewById(R.id.enter_sys);
		produce_key=(Button)findViewById(R.id.produce_key);
		decrypt_msg=(Button)findViewById(R.id.Receive_decrypt_msg);
		send_desmsg=(Button)findViewById(R.id.enter_sys_des);
		
		enter_system.setOnClickListener(this);
		produce_key.setOnClickListener(this);
		decrypt_msg.setOnClickListener(this);
		send_desmsg.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {  
        	case R.id.enter_sys:
        		Intent intent = new Intent();
            	intent.setClass(MainActivity.this,   
            			Send_SMS_Activity.class); 
            	startActivity(intent);
        		break;
        	case R.id.produce_key:
        		Intent intent_ = new Intent();
            	intent_.setClass(MainActivity.this,   
            			RSA_Key_Producer.class); 
            	startActivity(intent_);
        		break;
        		
        	case R.id.Receive_decrypt_msg:
        		Intent intent_1 = new Intent();
            	intent_1.setClass(MainActivity.this,   
            			Receive_SMS_Activity.class); 
            	startActivity(intent_1);
        		break;
        	case R.id.enter_sys_des:
        		Intent intent_2 = new Intent();
            	intent_2.setClass(MainActivity.this,   
            			Send_DES_Activity.class); 
            	startActivity(intent_2);
        		break;
        	default:
        		 break;
        }
	}

}
