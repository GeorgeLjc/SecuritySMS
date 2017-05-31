package com.ljc.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*连接数据库*/
public class SQLiteCon extends SQLiteOpenHelper{

	 private final static String DATABASE_NAME = "rsac_key";  
	 private final static int DATABASE_VERSION = 1;  
	 private final static String TABLE_NAME = "privateKey"; 
	
	public SQLiteCon(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	//建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME   
                + "(userName VARCHAR(30)  PRIMARY KEY,"   
                + " privateKey VARCHAR(200)," 
                + " pwd VARCHAR(20) NOT NULL)";  
        db.execSQL(sql);  
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		 String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;  
         db.execSQL(sql);  
         onCreate(db); 
	}

	//获取数据库游标  
    public Cursor select() {  
           SQLiteDatabase db = this.getReadableDatabase();  
           Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);  
           return cursor;  
    }  

    //插入一条记录  
    public long insert(String userName,String privateKey,String pwd ) {  
           SQLiteDatabase db = this.getWritableDatabase();  
           ContentValues cv = new ContentValues();  
           cv.put("userName", userName);  
           cv.put("privateKey", privateKey);  
           cv.put("pwd", pwd);
           long row = db.insert(TABLE_NAME, null, cv);  
           return row;  
    }  
    //更新
    public void update(String userName,String privateKey,String pwd )  
    {  
        SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues cv = new ContentValues();  
        cv.put("privateKey", privateKey);  
        cv.put("pwd", pwd);
//        db.execSQL("UPDATE privateKey SET privateKey = ? WHERE userName = ?",  
//            new String[]{privateKey,userName});
//        db.execSQL("UPDATE pwd SET pwd = ? WHERE userName = ?",  
//                new String[]{pwd,userName});
        String where = "userName = "+userName;
        db.update(TABLE_NAME, cv, where, null);
    } 
    
    //查询私钥  
    public String queryPrivateKey(String userName) {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT privateKey FROM "+TABLE_NAME+" WHERE userName = ?", new String[]{userName});  
        String privateKey = null;
        if (cursor.moveToFirst()) {            
           privateKey = cursor.getString(cursor.getColumnIndex("privateKey"));  
        }  
        return privateKey;  
    }
    /*查询密码*/
    public String queryUserPwd(String userName) {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT pwd FROM "+TABLE_NAME+" WHERE userName = ?", new String[]{userName});  
        String privateKey = "";
        if (cursor.moveToFirst()) {  
            privateKey = cursor.getString(cursor.getColumnIndex("pwd"));  
        }  
        return privateKey;  
    }
    
	public void delete(String UserName) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = " userName = ";
		String[] whereValue = { UserName };
		db.delete(TABLE_NAME, where, whereValue);
	}

//    //删除记录  
//    public void delete(int id) {  
//           SQLiteDatabase db = this.getWritableDatabase();  
//           String where ="_id = ?";  
//           String[] whereValue = { Integer.toString(id) };  
//           db.delete(TABLE_NAME, where, whereValue);  
//    }  
}
