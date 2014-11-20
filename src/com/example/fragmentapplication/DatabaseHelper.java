package com.example.fragmentapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static final String DB_NAME = "ListDatabase";
	public static final int DB_VERSION= 1;
	
	public DatabaseHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	private static final String CREATE = "create table items (_id integer primary key autoincrement , item_text text not null) ";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
}
