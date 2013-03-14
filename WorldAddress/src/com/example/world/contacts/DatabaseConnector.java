package com.example.world.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector {
	
	private static final String DB_NAME = "WorldContacts";
	private SQLiteDatabase database;
	private DatabaseOpenHelper dbOpenHelper;
	   
	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
	}
	
	   
	public void open() throws SQLException {  
		// open database in read/write mode  
		database = dbOpenHelper.getWritableDatabase();   
	} 

	   public void close() {
	      if (database != null)
	         database.close();
	   }	   
	   
	   public Cursor getAllContacts() {      
		   return database.query("contact", new String[] {"_id", "name"}, null, null, null, null, "name");	 
	   }
	   
	   public Cursor getOneContact(long id) {      
		   return database.query("contact", null, "_id=" + id, null, null, null, null);	  
	   }
			   	   
	   public void deleteContact(long id) {      
		   open();       
		   database.delete("contact", "_id=" + id, null);      
		   close();	  
	   }
	   
	   public void insertContact(String name, String addr, String code) {
		   open();      
		   database.insert("contact", null, content(name, addr, code));      
		   close();
	   }

	   public void updateContact(long id, String name, String addr,String code) {  
		   open();      
		   database.update("contact", content(name, addr, code), "_id=" + id, null);     
		   close();
	   }

	   private ContentValues content(String name, String addr, String code) {
		   ContentValues cValues = new ContentValues();
		   cValues.put("name", name);
		   cValues.put("addr", addr);      
		   cValues.put("code", code);
		   
		   return cValues;
	   }

}
