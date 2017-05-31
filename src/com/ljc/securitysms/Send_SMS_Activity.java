package com.ljc.securitysms;

import java.io.File;
import java.util.ArrayList;

import com.example.securitysms.R;
import com.ljc.util.FileUtil;
import com.ljc.util.RSAUtil;
import com.ljc.util.SQLiteCon;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class Send_SMS_Activity extends Activity  {

	EditText number,content;
	Button noncrypt_send,crypt_send,crypt_send_pubk;
	SmsManager sManager;
	String privateKey,UserPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_activity);
		//��ȡSmsManager����Ϣ������
		sManager = SmsManager.getDefault();
		//��ȡ�ı���Ͱ�ť
		number = (EditText)findViewById(R.id.phone_number);
		content = (EditText)findViewById(R.id.msg_content);
		noncrypt_send = (Button)findViewById(R.id.noncrypt_send);
		crypt_send = (Button)findViewById(R.id.crypt_send);
		crypt_send_pubk=(Button)findViewById(R.id.crypt_send_pubk);
		
		//Ϊ��ť����¼�
	
		noncrypt_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				//����һ��PendingIntent����
//				PendingIntent pi = PendingIntent.getActivity(Main_Activity.this, 0, new Intent(), 0);
//				//���Ͷ���
//				//���ž����������ƣ���������
//				 ArrayList<String> list = sManager.divideMessage(content.getText().toString());  //��Ϊһ���������������ƣ����Ҫ�������Ų��  
//		            for(String text:list){  
//						sManager.sendTextMessage(number.getText().toString(), null, text, pi, null);
//
//		            	
//		            }
//				sManager.sendTextMessage(number.getText().toString(), null, content.getText().toString(), pi, null);
//				//��ʾ���ŷ��ͳɹ�
//				Toast.makeText(Main_Activity.this, "���ŷ��ͳɹ�", 8000).show();
				
				if("".equals(number.getText())){
					Toast.makeText(Send_SMS_Activity.this, "�ռ��˲���Ϊ��", 8000).show();
				}
				else if("".equals(content.getText())){
					Toast.makeText(Send_SMS_Activity.this, "���ݲ���Ϊ��", 8000).show();
				}else{
					PendingIntent pi = PendingIntent.getActivity(Send_SMS_Activity.this, 0, new Intent(), 0);
					
					Toast.makeText(Send_SMS_Activity.this, number.getText().toString()+" "+content.getText().toString(), 8000).show();

					//���Ź��������÷ֿ鷢��
					if(content.getText().toString().length()>70){
						String msg_content="";
						msg_content+=content.getText().toString();
						ArrayList<String> msgs = sManager.divideMessage(msg_content);  
					    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
					    for(int i = 0;i<msgs.size();i++) 
					       sentIntents.add(pi);  
					    sManager.sendMultipartTextMessage(number.getText().toString(), null, msgs, sentIntents, null);  
					    Toast.makeText(Send_SMS_Activity.this, "���ŷ��ͳɹ�", 8000).show();
					}else{
						//���ų��ȷ���Ҫ�󣬵�������
						String msg_content="@msg:";
						msg_content+=content.getText().toString();
						sManager.sendTextMessage(number.getText().toString(), null, msg_content, pi, null);
					    Toast.makeText(Send_SMS_Activity.this, "���ŷ��ͳɹ�", 8000).show();
					}
				}
				
				
			}
		});
		crypt_send.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						//����һ��PendingIntent����
//						PendingIntent pi = PendingIntent.getActivity(Main_Activity.this, 0, new Intent(), 0);
//						//���Ͷ���
//						//���ž����������ƣ���������
//						 ArrayList<String> list = sManager.divideMessage(content.getText().toString());  //��Ϊһ���������������ƣ����Ҫ�������Ų��  
//				            for(String text:list){  
//								sManager.sendTextMessage(number.getText().toString(), null, text, pi, null);
//		
//				            	
//				            }
//						sManager.sendTextMessage(number.getText().toString(), null, content.getText().toString(), pi, null);
//						//��ʾ���ŷ��ͳɹ�
//						Toast.makeText(Main_Activity.this, "���ŷ��ͳɹ�", 8000).show();
						if("".equals(number.getText())){
							Toast.makeText(Send_SMS_Activity.this, "�ռ��˲���Ϊ��", 8000).show();
						}
						else if("".equals(content.getText())){
							Toast.makeText(Send_SMS_Activity.this, "���ݲ���Ϊ��", 8000).show();
						}else{
							/*��ø�����Ϣ*/
							String profile="";
							profile=FileUtil.readFileSdcard(FileUtil.Folder_NAME+"/profile.txt");
							
							/*���ݸ�����Ϣ�����ݿ��в����Լ���˽Կ*/
							if("".equals(profile)){
								Toast.makeText(Send_SMS_Activity.this, "������Ϣδ����", 8000).show();
							}else{
								SQLiteCon database=new SQLiteCon(Send_SMS_Activity.this);
								privateKey=database.queryPrivateKey(profile);
/**Test-1**/					//System.out.println("######Unit Test-1 @Main_Activity #privateKey ��" +privateKey);
								
								UserPwd=database.queryUserPwd(profile);
/**Test-2**/					//System.out.println("######Unit Test-2 @Main_Activity #UserPwd ��" +UserPwd);
								
/*Test through*/				//Toast.makeText(Send_SMS_Activity.this,UserPwd , 8000).show();
								
								if("".equals(privateKey)){
									Toast.makeText(Send_SMS_Activity.this, "δ����˽Կ", 8000).show();
								}else{
									Toast.makeText(Send_SMS_Activity.this,"���Ե� һ" , 8000).show();
									AlertDialog.Builder builder = new Builder(Send_SMS_Activity.this);
									builder.setTitle("���������");    //ͨ����������֤�û���Ϣ�Ӷ������Կ
									builder.setIcon(android.R.drawable.btn_star); 
									final EditText edit = new EditText(Send_SMS_Activity.this);
									edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
									builder.setView(edit);
									builder.setPositiveButton("ȷ��", 
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface arg0,int arg1) {
													String userpwd = edit.getText().toString();
													if(!userpwd.equals(UserPwd)){
														Toast.makeText(Send_SMS_Activity.this, "�����,���ʾܾ�", 8000).show();
													}else{//�û������֤ͨ��������ʹ��˽Կ
														String name = number.getText().toString();
														//String publicKey = "";
														/*��ȡ���շ���Կ*/
														//publicKey = FileUtil.readFileSdcard(FileUtil.File_NAME+name+".txt");
//														if("".equals(publicKey)){
//															Toast.makeText(Send_SMS_Activity.this, "ϵͳ���޴˽��շ���Կ", 8000).show();
//														}else{
/**Test-3**/											//System.out.println("######Unit Test-3 @Main_Activity #PublicKey ��" +publicKey);
														
														String message = content.getText().toString();
/**Test-4**/											//System.out.println("######Unit Test-4 @Main_Activity #message ��" +message);
														
														RSAUtil rsa=new RSAUtil();
														try {
															String ciphermessage="@msg_pri:";
															//ciphermessage += rsa.encryptByPublicKey(message, publicKey);
															SQLiteCon database=new SQLiteCon(Send_SMS_Activity.this);
															String profile="";
															profile=FileUtil.readFileSdcard(FileUtil.Folder_NAME+"/profile.txt");
															String private_key=database.queryPrivateKey(profile);
															
															ciphermessage += rsa.encryptByPrivateKey(message, private_key);
															//SQLiteCon database=new SQLiteCon(Send_SMS_Activity.this);
															//String profile="";
															//profile=FileUtil.readFileSdcard(FileUtil.Folder_NAME+"/profile.txt");
															
															//String private_key=database.queryPrivateKey(profile);
															//Toast.makeText(Send_SMS_Activity.this, private_key, 8000).show();
															
															//ciphermessage+=rsa.decryptByPrivateKey(ciphermessage_, private_key);
															
/**Test-5**/												//System.out.println("######Unit Test-5 @Main_Activity #Chiper_message ��" +ciphermessage);
															
															PendingIntent pi = PendingIntent.getActivity(Send_SMS_Activity.this, 0, new Intent(), 0);
															if (ciphermessage.length() > 70) {  
															    ArrayList<String> msgs = sManager.divideMessage(ciphermessage);  
															    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
															    for(int i = 0;i<msgs.size();i++){  
															       sentIntents.add(pi);  
															    }  
															    //sManager.sendTextMessage(number.getText().toString(), null, ciphermessage, pi, null);
															    sManager.sendMultipartTextMessage(number.getText().toString(), null, msgs, sentIntents, null);  
															    Toast.makeText(Send_SMS_Activity.this, "�������ĳɹ�>70", 8000).show();
															} else {   
																sManager.sendTextMessage(number.getText().toString(), null, ciphermessage, pi, null);  
																Toast.makeText(Send_SMS_Activity.this, "�������ĳɹ�<=70", 8000).show();
															}  
															//Toast.makeText(Send_SMS_Activity.this, "�������ĳɹ�", 8000).show();
														} catch (Exception e) {
															Toast.makeText(Send_SMS_Activity.this, "��������ʧ��", 8000).show();
															e.printStackTrace();
														}
														
														}
													}
												
									});
									builder.setNegativeButton("ȡ��",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											});
									builder.setCancelable(true); // ���ð�ť�Ƿ���԰����ؼ�ȡ��,false�򲻿���ȡ��
									AlertDialog dialog = builder.create(); // �����Ի���
									dialog.setCanceledOnTouchOutside(true); // ���õ�����ʧȥ�����Ƿ�����,��������������ط��Ƿ�����
									dialog.show();
								}
								
							}
						}
					}
				});
		
		crypt_send_pubk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SQLiteCon database=new SQLiteCon(Send_SMS_Activity.this);
				String receiver = number.getText().toString();
				String msg = content.getText().toString();
				
				if("".equals(receiver)){
					Toast.makeText(Send_SMS_Activity.this, "�ռ��˲���Ϊ��", 8000).show();
				}else if("".equals(msg)){
					Toast.makeText(Send_SMS_Activity.this, "�������ݲ���Ϊ��", 8000).show();
				}else{
					String publicKey="";
					publicKey = FileUtil.readFileSdcard(FileUtil.File_NAME+receiver+".txt");
					
					String ciphermsg="@msg_pub:";
					try {
						ciphermsg+=RSAUtil.encryptByPublicKey(msg, publicKey);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PendingIntent pi = PendingIntent.getActivity(Send_SMS_Activity.this, 0, new Intent(), 0);
					if (ciphermsg.length() > 70) {  
					    ArrayList<String> msgs = sManager.divideMessage(ciphermsg);  
					    ArrayList<PendingIntent> sentIntents =  new ArrayList<PendingIntent>();  
					    for(int i = 0;i<msgs.size();i++){  
					       sentIntents.add(pi);  
					    }  
					    //sManager.sendTextMessage(number.getText().toString(), null, ciphermessage, pi, null);
					    sManager.sendMultipartTextMessage(number.getText().toString(), null, msgs, sentIntents, null);  
					    Toast.makeText(Send_SMS_Activity.this, "�������ĳɹ�>70", 8000).show();
					} else {   
						sManager.sendTextMessage(number.getText().toString(), null, ciphermsg, pi, null);  
						Toast.makeText(Send_SMS_Activity.this, "�������ĳɹ�<=70", 8000).show();
					}
					
				}
			}
		});
		
	}

	
}
