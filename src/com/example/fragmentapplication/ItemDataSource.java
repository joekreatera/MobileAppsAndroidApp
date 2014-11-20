package com.example.fragmentapplication;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemDataSource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = {"_id" , "item_text"};
	
	public ItemDataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public void insertItem(int index){
		ContentValues values = new ContentValues();
		values.put("item_text" , "comentario " + index);
		
		long insertId = database.insert("items" , null, values);
		
		Cursor cursor = database.query("items", allColumns, "_id=" + insertId, null, null,null,null );
		
		cursor.moveToFirst();
		System.out.println("Registry " + cursor.getLong(0 ) + " with value " + cursor.getString(1) );
		cursor.close();
	}
	
	public List<String> getAllItems(){
		
		List<String> all = new ArrayList<String>();
		
		Cursor cursor = database.query("items", allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast() ){
			String val= cursor.getString(1);
			all.add(val);
			cursor.moveToNext();
		}
		
		cursor.close();
		return all;
	}
	
}
