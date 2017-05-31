package com.ljc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

public class FileUtil {


	public final static String Folder_NAME = "/sdcard/crypt_key"; 
	public final static String File_NAME = Folder_NAME+"/pkey"; 
	
	/*创建文件夹*/
	public static void folderCreate(){
		
		// 创建File对象  
		File dirFirstFolder = new File(Folder_NAME); 
		
		//如果该文件夹不存在，则进行创建     
		if(!dirFirstFolder.exists())  
	      dirFirstFolder.mkdirs();//创建文件夹  
	}
	
	/*创建文件*/
	public static void fileCreate(String name){
		// 创建File对象  
		File file = new File(name);   
		if(!file.exists()){  //文件不存在
	        try {  
	            file.createNewFile() ;  //创建文件 
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }    
	}
	
	/*在/mnt/sdcard/目录下写文件*/
	public static void writeFileSdcard(String name, String message) {
		try {
			//创建文件输出流
			FileOutputStream fout = new FileOutputStream(name);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*在/mnt/sdcard/目录下读文件*/
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
