package com.ljc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

public class FileUtil {


	public final static String Folder_NAME = "/sdcard/crypt_key"; 
	public final static String File_NAME = Folder_NAME+"/pkey"; 
	
	/*�����ļ���*/
	public static void folderCreate(){
		
		// ����File����  
		File dirFirstFolder = new File(Folder_NAME); 
		
		//������ļ��в����ڣ�����д���     
		if(!dirFirstFolder.exists())  
	      dirFirstFolder.mkdirs();//�����ļ���  
	}
	
	/*�����ļ�*/
	public static void fileCreate(String name){
		// ����File����  
		File file = new File(name);   
		if(!file.exists()){  //�ļ�������
	        try {  
	            file.createNewFile() ;  //�����ļ� 
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }    
	}
	
	/*��/mnt/sdcard/Ŀ¼��д�ļ�*/
	public static void writeFileSdcard(String name, String message) {
		try {
			//�����ļ������
			FileOutputStream fout = new FileOutputStream(name);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*��/mnt/sdcard/Ŀ¼�¶��ļ�*/
	public static String readFileSdcard(String name) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(name);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
