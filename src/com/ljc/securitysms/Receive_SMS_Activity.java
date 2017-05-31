package com.ljc.securitysms;

import com.example.securitysms.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;  
  
public class Receive_SMS_Activity extends Activity implements OnClickListener{  
	
    private TextView sender_phoneNumb,Msg_content;  
    private Button backward;
  
    // Message 类型值  
    private static final int MSG_AIRPLANE = 1;  
    private static final int MSG_OUTBOXCONTENT = 2;  
  
    private SmsObserver smsContentObserver;  
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.receive_activity);  /*find*/
        sender_phoneNumb = (TextView) findViewById(R.id.sender_resms);  /*find*/
        Msg_content = (TextView) findViewById(R.id.smscontent_resms);  
        backward = (Button)findViewById(R.id.receive_back);  /*find*/
        backward.setOnClickListener(this);
        // 创建两个对象  
        smsContentObserver = new SmsObserver(Receive_SMS_Activity.this,mHandler);  
        //注册内容观察者  
        registerContentObservers() ;  
    }  
  
    private void registerContentObservers() {  
        // ”表“内容观察者 ，通过测试我发现只能监听此Uri -----> content://sms  
        // 监听不到其他的Uri 比如说 content://sms/outbox  
        Uri smsUri = Uri.parse("content://sms");  
        getContentResolver().registerContentObserver(smsUri, true,smsContentObserver);  
    }  
  
    private Handler mHandler = new Handler() {  
  
        public void handleMessage(Message msg) {  
              
            System.out.println("---mHanlder----");  
            switch (msg.what) {  
            case MSG_OUTBOXCONTENT:  
                String outbox = (String) msg.obj;
                String[] out_box=outbox.split("&&&&");
                sender_phoneNumb.setText(out_box[0]);
                Msg_content.setText(out_box[1]);  
                break;  
            default:  
                break;  
            }  
        }  
    };
 
	@Override
	public void onClick(View v) {

		android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
		System.exit(0);

	}
}  
