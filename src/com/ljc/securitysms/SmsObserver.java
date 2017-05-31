package com.ljc.securitysms;


import java.security.PrivateKey;

import com.ljc.util.DESUtil;
import com.ljc.util.FileUtil;
import com.ljc.util.RSAUtil;
import com.ljc.util.SQLiteCon;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class SmsObserver extends ContentObserver
{
	private static String TAG = "SMSContentObserver";  
    
    private int MSG_OUTBOXCONTENT = 2 ;  
      
    private Context mContext  ;  
    private Handler mHandler ;   //����UI�߳�  
      
    public SmsObserver(Context context,Handler handler) {  
        super(handler);  
        mContext = context ;  
        mHandler = handler ;  
    }  
    /** 
     * ����������Uri�����ı�ʱ���ͻ�ص��˷��� 
     *  
     * @param selfChange  ��ֵ���岻�� һ������¸ûص�ֵfalse 
     */  
    @Override  
    public void onChange(boolean selfChange){  
          
      //��ȡ�ռ����еĶ��� 

        Uri outSMSUri = Uri.parse("content://sms/inbox") ;  
          
        Cursor c = mContext.getContentResolver().query(outSMSUri, null, null, null,"date desc");  
        
        if(c != null){  
              
            Log.i(TAG, "the number of send is"+c.getCount()) ;  
              
            StringBuilder sb = new StringBuilder() ;  
            //��ȡ���ݿ��е�һ������
            boolean hasDone =false;
            while(c.moveToNext()){  
//                sb.append("�������ֻ�����: "+c.getString(c.getColumnIndex("address")))  
//                  .append("��Ϣ����: "+c.getString(c.getColumnIndex("body")));  
                String address=c.getString(c.getColumnIndex("address"));
                String body=c.getString(c.getColumnIndex("body"));
                String decrypt_body="123";
                
                String[] sbody = body.split(":");
                if(sbody.length!=1){
                	if("@Public_key".equals(sbody[0])){
                		FileUtil.folderCreate();
            			FileUtil.writeFileSdcard(FileUtil.File_NAME+address.substring(3, address.length())+".txt",sbody[1]);
            			decrypt_body=sbody[1];
                	}
         /*bug*/ 	else if("@msg_pri".equals(sbody[0])){
        	 			String sender = address.substring(3);	
        	 			
                		String publicKey="";
    					publicKey = FileUtil.readFileSdcard(FileUtil.File_NAME+sender+".txt");//address �д���"+86"
                		//decrypt_body=sbody[1];
                		try {
                			//decrypt_body="123";
                			decrypt_body=RSAUtil.decryptByPublicKey(sbody[1], publicKey);
                			//decrypt_body="456";
                		} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		
                	}else if("@msg_pub".equals(sbody[0])){
                		SQLiteCon sql = new SQLiteCon(mContext);
                		String profile="";
                		profile=FileUtil.readFileSdcard(FileUtil.Folder_NAME+"/profile.txt");
                		
                		String private_key = sql.queryPrivateKey(profile);
                		//decrypt_body=sbody[1];
                		try {
                			//decrypt_body="123";
                			decrypt_body=RSAUtil.decryptByPrivateKey(sbody[1], private_key);
                			//decrypt_body="456";
                		} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}else if("@msg_des".equals(sbody[0])){
                		String sender=address.substring(3);
                		String deskey="";
                		deskey=FileUtil.readFileSdcard(FileUtil.File_NAME+"des"+sender+".txt");
                		
                		try {
                			decrypt_body+=deskey;
                			decrypt_body=DESUtil.decryptDES(sbody[1], deskey);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		
                		
                	}else if("@des_key".equals(sbody[0])){
                		SQLiteCon sql = new SQLiteCon(mContext);
                		String profile="";
                		profile=FileUtil.readFileSdcard(FileUtil.Folder_NAME+"/profile.txt");
                		
                		String private_key = sql.queryPrivateKey(profile);
                		
                		try {
                			//decrypt_body="123";
                			decrypt_body=RSAUtil.decryptByPrivateKey(sbody[1], private_key);
                			FileUtil.folderCreate();
                			FileUtil.writeFileSdcard(FileUtil.File_NAME+"des"+address.substring(3, address.length())+".txt",decrypt_body);
                			//decrypt_body="456";
                		} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}
                }
                sb.append(address)  
                .append("&&&&"+decrypt_body);
                decrypt_body="";
                if(sb.toString() !=null)//&& body.equals("��startMyActivity��"
                {
                  hasDone =true;
                  break;
                }
                if (hasDone)
                {
                  break;
                }
            }  
            c.close();            
            mHandler.obtainMessage(MSG_OUTBOXCONTENT, sb.toString()).sendToTarget();          
        }  
    }  
      
}  


